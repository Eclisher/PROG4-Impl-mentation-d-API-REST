package com.bank.implementation.Service;

import com.bank.implementation.Model.Account;
import com.bank.implementation.Repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signUp_Successful() {
        Account account = new Account();
        account.setAccountID(1);

        when(accountRepository.save(account)).thenReturn(account);

        String result = accountService.signUp(account);

        assertEquals("Sign-up successful", result);
    }

    @Test
    void signUp_Failed() {
        Account account = new Account();

        when(accountRepository.save(account)).thenReturn(null);

        String result = accountService.signUp(account);

        assertEquals("Sign-up failed", result);
    }

    @Test
    void getAllAccounts() {
        List<Account> expectedAccounts = new ArrayList<>();
        when(accountRepository.findAll()).thenReturn(expectedAccounts);

        List<Account> actualAccounts = accountService.getAllAccounts();

        assertEquals(expectedAccounts, actualAccounts);
    }

    @Test
    void signIn_Successful() {
        String accountNumber = "123";
        String password = "password";
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account());

        when(accountRepository.findByAccountNumberAndPassword(accountNumber, password)).thenReturn(accounts);

        boolean result = accountService.signIn(accountNumber, password);

        assertTrue(result);
    }

    @Test
    void signIn_Failed() {
        String accountNumber = "123";
        String password = "password";

        when(accountRepository.findByAccountNumberAndPassword(accountNumber, password)).thenReturn(new ArrayList<>());

        boolean result = accountService.signIn(accountNumber, password);

        assertFalse(result);
    }

    @Test
    void updateAccount() {
        Account account = new Account();

        when(accountRepository.update(account)).thenReturn(account);

        Account result = accountService.updateAccount(account);

        assertEquals(account, result);
    }

    @Test
    void findById() {
        Long accountId = 1L;
        Account expectedAccount = new Account();

        when(accountRepository.findById(accountId)).thenReturn(expectedAccount);

        Optional<Account> result = accountService.findById(accountId);

        assertTrue(result.isPresent());
        assertEquals(expectedAccount, result.get());
    }

    @Test
    void withdrawMoneyWithoutOverdraft() {
        // Arrange
        Long accountId = 1L;
        BigDecimal initialBalance = new BigDecimal("1000.00");
        BigDecimal withdrawalAmount = new BigDecimal("500.00");
        Account account = new Account();
        account.setAccountID(Math.toIntExact(accountId));
        account.setBalance(initialBalance);
        account.setOverdraftEnabled(false);

        when(accountRepository.findById(accountId)).thenReturn(account);
        when(accountRepository.update(account)).thenReturn(account);

        // Act
        accountService.withdrawMoneyWithOverdraft(accountId, withdrawalAmount);

        // Assert
        assertEquals(initialBalance.subtract(withdrawalAmount), account.getBalance());
    }

    @Test
    void withdrawMoneyWithOverdraft() {
        // Arrange
        Long accountId = 1L;
        BigDecimal initialBalance = new BigDecimal("1000.00");
        BigDecimal overdraftLimit = new BigDecimal("500.00");
        BigDecimal withdrawalAmount = new BigDecimal("1500.00");
        Account account = new Account();
        account.setAccountID(Math.toIntExact(accountId));
        account.setBalance(initialBalance);
        account.setOverdraftEnabled(true);
        account.setOverdraftLimit(overdraftLimit);

        when(accountRepository.findById(accountId)).thenReturn(account);
        when(accountRepository.update(account)).thenReturn(account);

        // Act
        accountService.withdrawMoneyWithOverdraft(accountId, withdrawalAmount);

        // Assert
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    void withdrawMoneyWithInsufficientBalance() {
        // Arrange
        Long accountId = 1L;
        BigDecimal initialBalance = new BigDecimal("1000.00");
        BigDecimal overdraftLimit = new BigDecimal("500.00");
        BigDecimal withdrawalAmount = new BigDecimal("2000.00");
        Account account = new Account();
        account.setAccountID(Math.toIntExact(accountId));
        account.setBalance(initialBalance);
        account.setOverdraftEnabled(true);
        account.setOverdraftLimit(overdraftLimit);

        when(accountRepository.findById(accountId)).thenReturn(account);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> accountService.withdrawMoneyWithOverdraft(accountId, withdrawalAmount));
    }

    @Test
    void withdrawMoneyWithNonExistingAccount() {
        // Arrange
        Long accountId = 1L;
        BigDecimal withdrawalAmount = new BigDecimal("500.00");

        when(accountRepository.findById(accountId)).thenReturn(null);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> accountService.withdrawMoneyWithOverdraft(accountId, withdrawalAmount));
    }


    @Test
    void getPrincipalBalance() {
        // Arrange
        Long accountId = 1L;
        BigDecimal initialBalance = new BigDecimal("1000.00");
        Account account = new Account();
        account.setAccountID(Math.toIntExact(accountId));
        account.setBalance(initialBalance);

        when(accountRepository.findById(accountId)).thenReturn(account);

        // Act
        BigDecimal principalBalance = accountService.getPrincipalBalance(account);

        // Assert
        assertEquals(initialBalance, principalBalance);
    }


    @Test

    void getLoanAmount() {
        // Arrange
        Long accountId = 1L;
        BigDecimal overdraftLimit = new BigDecimal("500.00");
        BigDecimal initialBalance = new BigDecimal("-200.00");
        Account account = new Account();
        account.setAccountID(Math.toIntExact(accountId));
        account.setBalance(initialBalance);
        account.setOverdraftLimit(overdraftLimit);
        account.setOverdraftEnabled(true);

        when(accountRepository.findById(accountId)).thenReturn(account);

        // Act
        BigDecimal loanAmount = accountService.getLoanAmount(account);

        // Assert
        assertEquals(new BigDecimal("200.00"), loanAmount);
    }


    @Test

    void getLoanInterest() {
        // Arrange
        Long accountId = 1L;
        BigDecimal initialBalance = new BigDecimal("-200.00");
        BigDecimal overdraftLimit = new BigDecimal("500.00");
        BigDecimal interestRateInitial = new BigDecimal("5.00");
        BigDecimal interestRateSubsequent = new BigDecimal("7.00");
        int maxOverdraftDays = 10;

        Account account = new Account();
        account.setAccountID(Math.toIntExact(accountId));
        account.setBalance(initialBalance);
        account.setOverdraftLimit(overdraftLimit);
        account.setInterestRateInitial(interestRateInitial);
        account.setInterestRateSubsequent(interestRateSubsequent);
        account.setMaxOverdraftDays(maxOverdraftDays);
        account.setOverdraftEnabled(true);

        when(accountRepository.findById(accountId)).thenReturn(account);

        // Act
        BigDecimal loanInterest = accountService.getLoanInterest(account);

        // Assert
        assertEquals(new BigDecimal("7.00"), loanInterest);
    }


    @Test
    void depositMoney() {
        // Arrange
        Long accountId = 1L;
        BigDecimal initialBalance = new BigDecimal("1000.00");
        BigDecimal depositAmount = new BigDecimal("500.00");

        Account account = new Account();
        account.setAccountID(Math.toIntExact(accountId));
        account.setBalance(initialBalance);

        when(accountRepository.findById(accountId)).thenReturn(account);

        // Act
        accountService.depositMoney(accountId, depositAmount);

        // Assert
        assertEquals(initialBalance.add(depositAmount), account.getBalance());
    }




    @Test
    void deleteById() {
        // Arrange
        Long accountId = 1L;

        Account account = new Account();
        account.setAccountID(Math.toIntExact(accountId));

        // Mock the behavior of findById to return the account
        when(accountRepository.findById(accountId)).thenReturn(account);

        // Mock the behavior of deleteById to return true indicating successful deletion
        when(accountRepository.deleteById(accountId)).thenReturn(true);

        // Act
        accountService.deleteById(accountId);

        // Assert
        // Verify that findById was called once with the specified accountId
        verify(accountRepository, times(1)).findById(accountId);
        // Verify that deleteById was called once with the specified accountId
        verify(accountRepository, times(1)).deleteById(accountId);
    }


}
