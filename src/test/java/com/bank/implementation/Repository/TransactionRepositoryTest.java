package com.bank.implementation.Repository;

import com.bank.implementation.Model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void testCreateTransaction() {
        // Create a new transaction
        LocalDateTime transactionDateTime = LocalDateTime.now();
        double amount = 100.00;
        String transactionType = "expense";
        String reason = "Groceries";
        LocalDateTime effectDate = LocalDateTime.now();
        String status = "completed";
        Long accountId = 1L;
        Long categoryId = 1L;

        // save the transaction in DB
        transactionRepository.createTransaction(transactionDateTime, BigDecimal.valueOf(amount), transactionType, reason, effectDate, status, accountId, categoryId);

        // Get all TRa,sactin
        List<Transaction> transactions = transactionRepository.getAllTransactions();

        // check if transaction was successfull
        assertThat(transactions).isNotEmpty();
        Transaction lastTransaction = transactions.get(transactions.size() - 1);
        assertThat(lastTransaction.getTransactionDateTime()).isEqualTo(transactionDateTime);
        assertThat(lastTransaction.getAmount()).isEqualTo(amount);
        assertThat(lastTransaction.getTransactionType()).isEqualTo(transactionType);
        assertThat(lastTransaction.getReason()).isEqualTo(reason);
        assertThat(lastTransaction.getEffectDate()).isEqualTo(effectDate);
        assertThat(lastTransaction.getStatus()).isEqualTo(status);
        assertThat(lastTransaction.getAccountId()).isEqualTo(accountId);
        assertThat(lastTransaction.getCategoryId()).isEqualTo(categoryId);
    }

    @Test
    public void testGetTransactionsInDateRange() {

        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();


        transactionRepository.createTransaction(LocalDateTime.now(), BigDecimal.valueOf(50.00), "expense", "Groceries", LocalDateTime.now(), "completed", 1L, 1L);
        transactionRepository.createTransaction(LocalDateTime.now().minusDays(5), BigDecimal.valueOf(100.00), "expense", "Electronics", LocalDateTime.now(), "completed", 1L, 2L);


        List<Transaction> transactions = transactionRepository.getTransactionsInDateRange(startDate, endDate);


        assertThat(transactions).isNotEmpty();
        assertThat(transactions.size()).isEqualTo(1);
        Transaction firstTransaction = transactions.get(0);
        assertThat(firstTransaction.getTransactionDateTime()).isBetween(startDate, endDate);
    }

    @Test
    public void testCreateDepositTransaction() {

        double amount = 200.00;
        String reason = "Salary";
        LocalDateTime effectDate = LocalDateTime.now();
        Long accountId = 1L;


        transactionRepository.createDepositTransaction(BigDecimal.valueOf(amount), reason, effectDate, accountId);


        List<Transaction> transactions = transactionRepository.getAllTransactions();

        assertThat(transactions).isNotEmpty();
        Transaction lastTransaction = transactions.get(transactions.size() - 1);
        assertThat(lastTransaction.getAmount()).isEqualTo(amount);
        assertThat(lastTransaction.getTransactionType()).isEqualTo("deposit");
        assertThat(lastTransaction.getReason()).isEqualTo(reason);
        assertThat(lastTransaction.getEffectDate()).isEqualTo(effectDate);
        assertThat(lastTransaction.getStatus()).isEqualTo("completed");
        assertThat(lastTransaction.getAccountId()).isEqualTo(accountId);
    }

    @Test
    public void testUpdateTransactionCategory() {

        transactionRepository.createTransaction(LocalDateTime.now(), BigDecimal.valueOf(100.00), "expense", "Shopping", LocalDateTime.now(), "completed", 1L, 1L);


        List<Transaction> transactions = transactionRepository.getAllTransactions();


        Transaction firstTransaction = transactions.get(0);
        Long transactionId = firstTransaction.getTransactionId();
        Long newCategoryId = 2L;


        transactionRepository.updateTransactionCategory(transactionId, newCategoryId);


        Transaction updatedTransaction = transactionRepository.getAllTransactions().stream()
                .filter(t -> t.getTransactionId().equals(transactionId))
                .findFirst()
                .orElse(null);


        assertThat(updatedTransaction).isNotNull();
        assertThat(updatedTransaction.getCategoryId()).isEqualTo(newCategoryId);
    }
}
