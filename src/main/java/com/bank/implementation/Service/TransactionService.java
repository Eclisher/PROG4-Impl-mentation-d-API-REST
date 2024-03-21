package com.bank.implementation.Service;

import com.bank.implementation.Model.Transaction;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface TransactionService {
    void createTransaction(LocalDateTime transactionDateTime, double amount, String transactionType, String reason, LocalDateTime effectDate, String status) throws SQLException;
    void transferMoney(String fromAccount, String toAccount, double amount) throws SQLException;
    List<Transaction> getAllTransactions() throws SQLException;
}
