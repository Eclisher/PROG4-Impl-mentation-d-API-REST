package com.bank.implementation.Model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {

    @Test
    public void testAccountConstructorAndGettersSetters() {
        // Create an account instance
        Account account = new Account();

        // define the values for prompt
        String clientFirstName = "John";
        String clientLastName = "Doe";
        String password = "password123";
        Date clientDateOfBirth = new Date();
        BigDecimal monthlyNetSalary = new BigDecimal("5000.00");
        BigDecimal balance = new BigDecimal("1000.00");
        Date creationDate = new Date();
        Date modificationDate = new Date();
        boolean overdraftEnabled = true;
        BigDecimal overdraftLimit = new BigDecimal("100.00");
        BigDecimal interestRateInitial = new BigDecimal("0.05");
        BigDecimal interestRateSubsequent = new BigDecimal("0.03");
        int maxOverdraftDays = 30;

        account.setClientFirstName(clientFirstName);
        account.setClientLastName(clientLastName);
        account.setPassword(password);
        account.setClientDateOfBirth(clientDateOfBirth);
        account.setMonthlyNetSalary(monthlyNetSalary);
        account.setBalance(balance);
        account.setCreationDate(creationDate);
        account.setModificationDate(modificationDate);
        account.setOverdraftEnabled(overdraftEnabled);
        account.setOverdraftLimit(overdraftLimit);
        account.setInterestRateInitial(interestRateInitial);
        account.setInterestRateSubsequent(interestRateSubsequent);
        account.setMaxOverdraftDays(maxOverdraftDays);


        assertEquals(clientFirstName, account.getClientFirstName());
        assertEquals(clientLastName, account.getClientLastName());
        assertEquals(password, account.getPassword());
        assertEquals(clientDateOfBirth, account.getClientDateOfBirth());
        assertEquals(monthlyNetSalary, account.getMonthlyNetSalary());
        assertEquals(balance, account.getBalance());
        assertEquals(creationDate, account.getCreationDate());
        assertEquals(modificationDate, account.getModificationDate());
        assertEquals(overdraftEnabled, account.isOverdraftEnabled());
        assertEquals(overdraftLimit, account.getOverdraftLimit());
        assertEquals(interestRateInitial, account.getInterestRateInitial());
        assertEquals(interestRateSubsequent, account.getInterestRateSubsequent());
        assertEquals(maxOverdraftDays, account.getMaxOverdraftDays());
    }
}
