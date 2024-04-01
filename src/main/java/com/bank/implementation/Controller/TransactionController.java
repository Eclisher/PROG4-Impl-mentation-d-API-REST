package com.bank.implementation.Controller;

import com.bank.implementation.Model.Transaction;
import com.bank.implementation.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bank.implementation.Service.TransactionService;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping("/create")
    public String createTransaction(@RequestBody Transaction transaction) {
        transactionRepository.createTransaction(
                transaction.getTransactionDateTime(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getReason(),
                transaction.getEffectDate(),
                transaction.getStatus(),
                transaction.getAccountId(),
                transaction.getCategoryId()
        );
        return "Transaction created successfully";
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
    public String createDepositTransaction(@RequestBody Transaction transaction) {
        transactionService.createDepositTransaction(
                transaction.getAmount(),
                transaction.getReason(),
                transaction.getEffectDate(),
                transaction.getAccountId()
        );
        return "Deposit Transaction created successfully";
    }
}

