package com.bank.implementation.Service;
import com.bank.implementation.Model.Account;
import com.bank.implementation.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Account updateAccount(Long accountId, Account accountDetails) {
        Optional<Account> optionalAccount = Optional.ofNullable(accountRepository.findById(accountId));

        if (optionalAccount.isPresent()) {
            Account existingAccount = optionalAccount.get();
            existingAccount.setClientFirstName(accountDetails.getClientFirstName());
            existingAccount.setClientDateOfBirth(accountDetails.getClientDateOfBirth());
            existingAccount.setMonthlyNetSalary(accountDetails.getMonthlyNetSalary());
            existingAccount.setOverdraftEnabled(accountDetails.isOverdraftEnabled());
            existingAccount.setOverdraftLimit(accountDetails.getOverdraftLimit());
            existingAccount.setInterestRateInitial(accountDetails.getInterestRateInitial());
            existingAccount.setInterestRateSubsequent(accountDetails.getInterestRateSubsequent());
            existingAccount.setMaxOverdraftDays(accountDetails.getMaxOverdraftDays());
            existingAccount.setModificationDate(new Date());
            return accountRepository.save(existingAccount);
        } else {
            throw new RuntimeException("Compte non trouvé avec l'ID : " + accountId);
        }
    }
    public Optional<Account> findById(Long accountId) {
        return Optional.ofNullable(accountRepository.findById(accountId));
    }

    public void withdrawMoney(Account account, BigDecimal amount) {
        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.subtract(amount);
        account.setBalance(newBalance);
        account.setModificationDate(new Date());
        accountRepository.save(account);
    }

    public BigDecimal getPrincipalBalance(Account account) {
        return account.getBalance();
    }

    public BigDecimal getLoanAmount(Account account) {
        if (account.isOverdraftEnabled()) {
            BigDecimal overdraftLimit = account.getOverdraftLimit();
            BigDecimal currentBalance = account.getBalance();
            if (currentBalance.compareTo(BigDecimal.ZERO) < 0) {
                return currentBalance.abs().min(overdraftLimit);
            } else {
                return BigDecimal.ZERO;
            }
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getLoanInterest(Account account) {
        BigDecimal loanAmount = getLoanAmount(account);
        BigDecimal interestRateInitial = account.getInterestRateInitial();
        BigDecimal interestRateSubsequent = account.getInterestRateSubsequent();
        BigDecimal interest;
        if (loanAmount.compareTo(BigDecimal.ZERO) > 0) {
            int maxOverdraftDays = account.getMaxOverdraftDays();
            if (maxOverdraftDays <= 7) {
                // Calcul des intérêts pour les 7 premiers jours
                interest = loanAmount.multiply(interestRateInitial.divide(BigDecimal.valueOf(100)));
            } else {
                // Calcul des intérêts pour les jours suivants
                BigDecimal interestRateForSubsequentDays = interestRateSubsequent.divide(BigDecimal.valueOf(100));
                // Calcul du nombre de jours pour lesquels les intérêts doivent être calculés
                int numberOfSubsequentDays = maxOverdraftDays - 7;
                // Calcul des intérêts pour les jours suivants
                interest = loanAmount.multiply(interestRateForSubsequentDays).multiply(BigDecimal.valueOf(numberOfSubsequentDays));
            }
        } else {
            interest = BigDecimal.ZERO;
        }
        return interest;
    }

}

