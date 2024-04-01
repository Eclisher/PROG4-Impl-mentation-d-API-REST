package com.bank.implementation.Controller;

import com.bank.implementation.Controller.TransactionController;
import com.bank.implementation.Model.Transaction;
import com.bank.implementation.Service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
// TODO : implement some test  to fit the transaction testing
    @Test
    void testCreateTransaction() {
        Transaction transaction = new Transaction(/* initialize Transaction here */);

        transactionController.createTransaction(transaction);

        verify(transactionService, times(1)).createTransaction(
                transaction.getTransactionDateTime(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getReason(),
                transaction.getEffectDate(),
                transaction.getStatus()
        );
    }

    @Test
    void testGetAllTransactions() {
        List<Transaction> expectedTransactions = new ArrayList<>();
        // Add some transactions to expectedTransactions

        when(transactionService.getAllTransactions()).thenReturn(expectedTransactions);

        List<Transaction> result = transactionController.getAllTransactions();

        assertEquals(expectedTransactions.size(), result.size());
        verify(transactionService, times(1)).getAllTransactions();
    }

    @Test
    void testGetTransactionsInDateRange() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();
        List<Transaction> expectedTransactions = new ArrayList<>();
        // Add some transactions to expectedTransactions

        when(transactionService.getTransactionsInDateRange(startDate, endDate)).thenReturn(expectedTransactions);

        List<Transaction> result = transactionController.getTransactionsInDateRange(startDate, endDate);

        assertEquals(expectedTransactions.size(), result.size());
        verify(transactionService, times(1)).getTransactionsInDateRange(startDate, endDate);
    }

    @Test
    void testCreateDepositTransaction() {
        Transaction transaction = new Transaction(/* initialize Transaction here */);

        transactionController.createDepositTransaction(transaction);

        verify(transactionService, times(1)).createDepositTransaction(
                transaction.getAmount(),
                transaction.getReason(),
                transaction.getEffectDate(),
                transaction.getAccountId()
        );
    }
}
