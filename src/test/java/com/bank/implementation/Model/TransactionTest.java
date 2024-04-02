package com.bank.implementation.Model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {

    @Test
    public void testTransactionConstructorAndGettersSetters() {

        Transaction transaction = new Transaction();


        Long transactionId = 1L;
        LocalDateTime transactionDateTime = LocalDateTime.now();
        double amount = 1000.00;
        String transactionType = "DEPOSIT";
        String reason = "Salary";
        LocalDateTime effectDate = LocalDateTime.now();
        LocalDateTime registrationDate = LocalDateTime.now();
        String status = "SUCCESS";
        Long accountId = 123L;
        Long categoryId = 456L;

        transaction.setTransactionId(transactionId);
        transaction.setTransactionDateTime(transactionDateTime);
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transaction.setReason(reason);
        transaction.setEffectDate(effectDate);
        transaction.setRegistrationDate(registrationDate);
        transaction.setStatus(status);
        transaction.setAccountId(accountId);
        transaction.setCategoryId(categoryId);

        // check the correct return values
        assertEquals(transactionId, transaction.getTransactionId());
        assertEquals(transactionDateTime, transaction.getTransactionDateTime());
        assertEquals(amount, transaction.getAmount());
        assertEquals(transactionType, transaction.getTransactionType());
        assertEquals(reason, transaction.getReason());
        assertEquals(effectDate, transaction.getEffectDate());
        assertEquals(registrationDate, transaction.getRegistrationDate());
        assertEquals(status, transaction.getStatus());
        assertEquals(accountId, transaction.getAccountId());
        assertEquals(categoryId, transaction.getCategoryId());
    }
}
