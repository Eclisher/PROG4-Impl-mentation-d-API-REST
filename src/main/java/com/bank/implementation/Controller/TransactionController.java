package com.bank.implementation.Controller;

import com.bank.implementation.Model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bank.implementation.Service.TransactionService;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public void createTransaction(@RequestBody Transaction transaction) {
        transactionService.createTransaction(
                transaction.getTransactionDateTime(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getReason(),
                transaction.getEffectDate(),
                transaction.getStatus()
        );
    }

    @GetMapping("/all")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/date-range")
    public List<Transaction> getTransactionsInDateRange(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return transactionService.getTransactionsInDateRange(startDate, endDate);
    }

    @PostMapping("/deposit")
    public void createDepositTransaction(@RequestBody Transaction transaction) {
        transactionService.createDepositTransaction(
                transaction.getAmount(),
                transaction.getReason(),
                transaction.getEffectDate(),
                transaction.getAccountId()
        );
    }
}

