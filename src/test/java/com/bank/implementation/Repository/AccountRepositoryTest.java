package com.bank.implementation.Repository;

import com.bank.implementation.Model.Account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountRepositoryTest {

    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        // You may need to mock the database connection or use an in-memory database for testing
        // For simplicity, let's assume the accountRepository instance is properly initialized with a mocked connection
        accountRepository = new AccountRepository();
    }

    @Test
    public void testSaveAccount() {
        // Create a new account
        Account account = new Account();
        account.setAccountNumber("123456789");
        account.setClientLastName("Doe");
        account.setClientFirstName("John");
        account.setPassword("password");
        account.setMonthlyNetSalary(new BigDecimal("5000.00"));
        account.setOverdraftEnabled(true);
        account.setOverdraftLimit(new BigDecimal("1000.00"));
        account.setInterestRateInitial(new BigDecimal("0.05"));
        account.setInterestRateSubsequent(new BigDecimal("0.03"));
        account.setMaxOverdraftDays(7);
        account.setBalance(new BigDecimal("500.00"));

        // Save the account
        Account savedAccount = accountRepository.save(account);

        // Check if the account was saved successfully
        assertThat(savedAccount).isNotNull();
        assertThat(savedAccount.getAccountID()).isNotNull();
    }

    @Test
    public void testFindAllAccounts() {
        // Retrieve all accounts from the repository
        List<Account> accounts = accountRepository.findAll();

        // Check if the list of accounts is not empty
        assertThat(accounts).isNotEmpty();
    }

    @Test
    public void testFindByAccountNumberAndPassword() {
        // Define account number and password
        String accountNumber = "123456789";
        String password = "password";

        // Retrieve accounts by account number and password
        List<Account> accounts = accountRepository.findByAccountNumberAndPassword(accountNumber, password);

        // Check if the list of accounts is not empty
        assertThat(accounts).isNotEmpty();
    }

    @Test
    public void testFindById() {
        // Assume we have an existing account with ID 1
        Long accountId = 1L;

        // Retrieve the account by ID
        Account account = accountRepository.findById(accountId);

        // Check if the retrieved account is not null
        assertThat(account).isNotNull();
        assertThat(account.getAccountID()).isEqualTo(accountId);
    }

    @Test
    public void testDeleteById() {
        // Assume we have an existing account with ID 1
        Long accountId = 1L;

        // Delete the account by ID
        boolean isDeleted = accountRepository.deleteById(accountId);

        // Check if the account is deleted successfully
        assertThat(isDeleted).isTrue();
    }

    @Test
    public void testUpdateAccount() {
        // Assume we have an existing account with ID 1
        Long accountId = 1L;

        // Retrieve the account by ID
        Account account = accountRepository.findById(accountId);

        // Update the account details
        account.setAccountNumber("987654321");
        account.setClientLastName("Smith");

        // Save the updated account
        Account updatedAccount = accountRepository.update(account);

        // Retrieve the account again by ID to check if it's updated
        Account retrievedUpdatedAccount = accountRepository.findById(accountId);

        // Check if the account is updated successfully
        assertThat(updatedAccount).isNotNull();
        assertThat(retrievedUpdatedAccount).isNotNull();
        assertThat(retrievedUpdatedAccount.getAccountNumber()).isEqualTo("987654321");
        assertThat(retrievedUpdatedAccount.getClientLastName()).isEqualTo("Smith");
    }

    @Test
    public void testSaveOrUpdateAccount() {

        // Create a new account
        Account account = new Account();
        account.setAccountNumber("123456789");
        account.setClientLastName("Doe");
        account.setClientFirstName("Jane");
        account.setPassword("password");
        account.setMonthlyNetSalary(new BigDecimal("6000.00"));
        account.setOverdraftEnabled(true);
        account.setOverdraftLimit(new BigDecimal("1500.00"));
        account.setInterestRateInitial(new BigDecimal("0.04"));
        account.setInterestRateSubsequent(new BigDecimal("0.02"));
        account.setMaxOverdraftDays(7);
        account.setBalance(new BigDecimal("1000.00"));

        // Save or update the account
        Account savedOrUpdateAccount = accountRepository.saveOrUpdateAccount(account);

        // Check if the account was saved or updated successfully
        assertThat(savedOrUpdateAccount).isNotNull();

        // Retrieve the account by ID to verify if it's saved or updated
        Account retrievedAccount = accountRepository.findById(savedOrUpdateAccount.getAccountID());

        // Check if the retrieved account matches the saved or updated account
        assertThat(retrievedAccount).isNotNull();
        assertThat(retrievedAccount.getAccountID()).isEqualTo(savedOrUpdateAccount.getAccountID());
        assertThat(retrievedAccount.getAccountNumber()).isEqualTo(savedOrUpdateAccount.getAccountNumber());
        assertThat(retrievedAccount.getClientLastName()).isEqualTo(savedOrUpdateAccount.getClientLastName());
        assertThat(retrievedAccount.getClientFirstName()).isEqualTo(savedOrUpdateAccount.getClientFirstName());
        assertThat(retrievedAccount.getPassword()).isEqualTo(savedOrUpdateAccount.getPassword());
        assertThat(retrievedAccount.getMonthlyNetSalary()).isEqualTo(savedOrUpdateAccount.getMonthlyNetSalary());
        assertThat(retrievedAccount.isOverdraftEnabled()).isEqualTo(savedOrUpdateAccount.isOverdraftEnabled());
        assertThat(retrievedAccount.getOverdraftLimit()).isEqualTo(savedOrUpdateAccount.getOverdraftLimit());
        assertThat(retrievedAccount.getInterestRateInitial()).isEqualTo(savedOrUpdateAccount.getInterestRateInitial());
        assertThat(retrievedAccount.getInterestRateSubsequent()).isEqualTo(savedOrUpdateAccount.getInterestRateSubsequent());
        assertThat(retrievedAccount.getMaxOverdraftDays()).isEqualTo(savedOrUpdateAccount.getMaxOverdraftDays());
        assertThat(retrievedAccount.getBalance()).isEqualTo(savedOrUpdateAccount.getBalance());
    }


}
