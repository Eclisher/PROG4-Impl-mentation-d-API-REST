package com.bank.implementation.Controller;

import com.bank.implementation.Model.Transaction;
import com.bank.implementation.Service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public void createTransaction(@RequestBody Transaction transaction) throws SQLException {
        transactionService.createTransaction(transaction.getTransactionDateTime(), transaction.getAmount(), transaction.getTransactionType(), transaction.getReason(), transaction.getEffectDate(), transaction.getStatus());
    }

    @PostMapping("/transfer")
    public void transferMoney(@RequestParam String fromAccount, @RequestParam String toAccount, @RequestParam double amount) throws SQLException {
        transactionService.transferMoney(fromAccount, toAccount, amount);
    }

    @GetMapping("/all")
    public List<Transaction> getAllTransactions() throws SQLException {
        return transactionService.getAllTransactions();
    }
}
