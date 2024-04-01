package com.bank.implementation.Controller;

import com.bank.implementation.Model.Account;
import com.bank.implementation.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {
    @GetMapping("")
    public  String Welcome(){
        return "Votre serveur est  bien démarrer";
    }

    @GetMapping("/ping")
    public  String Pingpong(){
        return  "pong";
    }

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestBody Account account) {
        String signUpResult = accountService.signUp(account);
        return signUpResult;
    }


    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId) {
        Optional<Account> accountOptional = accountService.findById(accountId);
        return accountOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody SignInRequest signInRequest) {
        boolean signInResult = accountService.signIn(signInRequest.getAccountNumber(), signInRequest.getPassword());
        if (signInResult) {
            return ResponseEntity.ok("Connexion réussie !");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de la connexion. Verifier votre AccountNumber et Password");
        }
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") Long id, @RequestBody Map<String, Object> accountDetails) {
        Optional<Account> existingAccountOptional = accountService.findById(id);

        if (!existingAccountOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Account existingAccount = existingAccountOptional.get();

        for (Map.Entry<String, Object> entry : accountDetails.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();
            try {
                Field field = Account.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                if (value != null) {
                    if (field.getType().equals(Date.class) && value instanceof String) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                        Date parsedDate = (Date) dateFormat.parse((String) value);
                        field.set(existingAccount, parsedDate);
                    } else {
                        field.set(existingAccount, value);
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException | ParseException e) {
                e.printStackTrace();
            }
        }

        Account updatedAccount = accountService.updateAccount(existingAccount);

        return ResponseEntity.ok(updatedAccount);
    }



    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<String> deleteAccountById(@PathVariable("id") Long accountId) {
        try {
            accountService.deleteById(accountId);
            return ResponseEntity.ok("Compte supprimé avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<?> withdrawMoney(@PathVariable Long accountId, @RequestBody WithdrawalRequest withdrawalRequest) {
        try {
            Optional<Account> optionalAccount = accountService.findById(accountId);
            if (!optionalAccount.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            Account account = optionalAccount.get();
            boolean overdraftEnabled = account.isOverdraftEnabled();
            BigDecimal balance = account.getBalance();
            BigDecimal withdrawalAmount = withdrawalRequest.getAmount();

            if (balance.compareTo(withdrawalAmount) >= 0 || (overdraftEnabled && balance.add(account.getOverdraftLimit()).compareTo(withdrawalAmount) >= 0)) {
                accountService.withdrawMoneyWithOverdraft(accountId, withdrawalAmount);
                return ResponseEntity.ok("Retrait d'argent effectué avec succès.");
            } else {
                return ResponseEntity.badRequest().body("Solde insuffisant pour effectuer ce retrait.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue lors du retrait d'argent.");
        }
    }


    @GetMapping("/{accountId}/balances")
    public ResponseEntity<AccountBalancesResponse> getAccountBalances(@PathVariable Long accountId) {
        try {
            Account account = accountService.findById(accountId)
                    .orElseThrow(() -> new RuntimeException("Compte non trouvé avec l'ID : " + accountId));

            BigDecimal principalBalance = accountService.getPrincipalBalance(account);
            BigDecimal loanAmount = accountService.getLoanAmount(account);
            BigDecimal loanInterest = accountService.getLoanInterest(account);

            AccountBalancesResponse response = new AccountBalancesResponse();
            response.setPrincipalBalance(principalBalance);
            response.setLoanAmount(loanAmount);
            response.setLoanInterest(loanInterest);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<String> depositMoney(@PathVariable Long accountId, @RequestBody DepositRequest depositRequest) {
        try {
            accountService.depositMoney(accountId, depositRequest.getAmount());
            return ResponseEntity.ok("Approvisionnement de solde effectué avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue lors de l'approvisionnement de solde.");
        }
    }
}

