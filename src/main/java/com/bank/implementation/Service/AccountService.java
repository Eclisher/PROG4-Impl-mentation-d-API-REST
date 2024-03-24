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

    public Account updateAccount(Long id, Account account) {
        return accountRepository.update(account);
    }
    public Optional<Account> findById(Long accountId) {
        return Optional.ofNullable(accountRepository.findById(accountId));
    }

    public void withdrawMoneyWithOverdraft(Long accountId, BigDecimal amount) {
        try{
            Optional<Account> optionalAccount = Optional.ofNullable(accountRepository.findById(accountId));
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            BigDecimal currentBalance = account.getBalance();
            BigDecimal overdraftLimit = account.getOverdraftLimit();
            boolean overdraftEnabled = account.isOverdraftEnabled();
            if (!overdraftEnabled && currentBalance.compareTo(amount) < 0) {
                throw new RuntimeException("Solde insuffisant pour effectuer ce retrait.");
            }
            BigDecimal totalAvailableBalance = currentBalance.add(overdraftLimit);

            if (totalAvailableBalance.compareTo(amount) < 0) {
                throw new RuntimeException("Solde insuffisant pour effectuer ce retrait.");
            }

            BigDecimal newBalance = currentBalance.subtract(amount);
            account.setBalance(newBalance);
            account.setModificationDate(new Date());
            BigDecimal interestRate = BigDecimal.ZERO;
            int maxOverdraftDays = account.getMaxOverdraftDays();
            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                // Solde négatif, calcul des intérêts
                BigDecimal overdraftAmount = newBalance.abs();
                BigDecimal initialInterest = overdraftAmount.multiply(account.getInterestRateInitial().divide(BigDecimal.valueOf(100)));
                BigDecimal subsequentInterest = BigDecimal.ZERO;
                if (maxOverdraftDays > 7) {
                    int subsequentDays = maxOverdraftDays - 7;
                    BigDecimal subsequentInterestRate = account.getInterestRateSubsequent().divide(BigDecimal.valueOf(100));
                    subsequentInterest = overdraftAmount.multiply(subsequentInterestRate).multiply(BigDecimal.valueOf(subsequentDays));
                }
                interestRate = initialInterest.add(subsequentInterest);
            }
            account.setInterest(interestRate);
            accountRepository.update(account);
        }
            else {
                throw new RuntimeException("Compte non trouvé avec l'ID : " + accountId);
            }
        } catch (Exception e) {
          throw new RuntimeException();
        }
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
    public void depositMoney(Long accountId, BigDecimal amount) {
        Optional<Account> optionalAccount = Optional.ofNullable(accountRepository.findById(accountId));
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            BigDecimal currentBalance = account.getBalance();
            BigDecimal newBalance = currentBalance.add(amount);
            account.setBalance(newBalance);
            accountRepository.save(account);
        } else {
            throw new RuntimeException("Compte non trouvé avec l'ID : " + accountId);
        }
    }
    public void deleteById(Long accountId) {
        Optional<Account> optionalAccount = Optional.ofNullable(accountRepository.findById(accountId));
        if (optionalAccount.isPresent()) {
            accountRepository.deleteById(accountId);
        } else {
            throw new RuntimeException("Compte non trouvé avec l'ID : " + accountId);
        }
    }

}

