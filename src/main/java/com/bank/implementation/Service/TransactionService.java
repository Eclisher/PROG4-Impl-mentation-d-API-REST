package com.bank.implementation.Service;

import com.bank.implementation.Model.Transaction;
import com.bank.implementation.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    private  TransactionRepository transactionRepository;

    @Autowired
    public void TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void createTransaction(LocalDateTime transactionDateTime, double amount, String transactionType, String reason, LocalDateTime effectDate, String status) {
        transactionRepository.createTransaction(transactionDateTime, amount, transactionType, reason, effectDate, status);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }

    public List<Transaction> getTransactionsInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.getTransactionsInDateRange(startDate, endDate);
    }

    public void createDepositTransaction(double amount, String reason, LocalDateTime effectDate, Long accountId) {
        transactionRepository.createDepositTransaction(amount, reason, effectDate, accountId);
    }
}
