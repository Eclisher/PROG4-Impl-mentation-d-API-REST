package com.bank.implementation.Service;
import com.bank.implementation.Model.Account;
import com.bank.implementation.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String signUp(Account account) {
        Account savedAccount = accountRepository.save(account);
        return savedAccount != null ? "Sign-up successful" : "Sign-up failed";
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    public boolean signIn(String accountNumber, String password) {
        List<Account> accounts = accountRepository.findByAccountNumberAndPassword(accountNumber, password);
        return !accounts.isEmpty();
    }
}

