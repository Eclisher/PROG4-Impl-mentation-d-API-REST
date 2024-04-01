package com.bank.implementation.Model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatementTest {

    @Test
    public void testStatementConstructorAndGettersSetters() {

        Statement statement = new Statement();


        int statementID = 1;
        LocalDateTime transactionDateTime = LocalDateTime.now();
        String reference = "Ref123";
        String transactionType = "DEPOSIT";
        BigDecimal amount = BigDecimal.valueOf(1000.00);
        BigDecimal principalBalance = BigDecimal.valueOf(5000.00);
        int accountID = 123;

        statement.setStatementID(statementID);
        statement.setTransactionDateTime(transactionDateTime);
        statement.setReference(reference);
        statement.setTransactionType(transactionType);
        statement.setAmount(amount);
        statement.setPrincipalBalance(principalBalance);
        statement.setAccountID(accountID);


        assertEquals(statementID, statement.getStatementID());
        assertEquals(transactionDateTime, statement.getTransactionDateTime());
        assertEquals(reference, statement.getReference());
        assertEquals(transactionType, statement.getTransactionType());
        assertEquals(amount, statement.getAmount());
        assertEquals(principalBalance, statement.getPrincipalBalance());
        assertEquals(accountID, statement.getAccountID());
    }
}
