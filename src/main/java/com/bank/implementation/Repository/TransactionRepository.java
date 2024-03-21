package com.bank.implementation.Repository;
import com.bank.implementation.Model.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionRepository {
    void saveTransaction(Transaction transaction) throws SQLException;
    List<Transaction> getAllTransactions() throws SQLException;
}
