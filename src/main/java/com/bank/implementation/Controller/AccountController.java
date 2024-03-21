package com.bank.implementation.Controller;

import com.bank.implementation.Model.Account;
import com.bank.implementation.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}

