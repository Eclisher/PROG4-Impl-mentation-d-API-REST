package com.bank.implementation.Controller;

import com.bank.implementation.Model.Account;
import com.bank.implementation.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
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

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody SignInRequest signInRequest) {
        boolean signInResult = accountService.signIn(signInRequest.getAccountNumber(), signInRequest.getPassword());
        if (signInResult) {
            return ResponseEntity.ok("Connexion réussie !");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de la connexion. Verifier votre AccountNumber et Password");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") Long id, @RequestBody Account accountDetails) {
        Account updatedAccount = accountService.updateAccount(id, accountDetails);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
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
                accountService.withdrawMoney(account, withdrawalAmount);
                return ResponseEntity.ok("Retrait d'argent effectué avec succès.");
            } else {
                return ResponseEntity.badRequest().body("Solde insuffisant pour effectuer ce retrait.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue lors du retrait d'argent.");
        }
    }
}

