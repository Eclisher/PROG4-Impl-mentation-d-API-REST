package com.bank.implementation.Repository;

import com.bank.implementation.Model.Transaction;
import com.bank.implementation.PostgresqlConnection;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepository {

    private static final String TRANSACTION_TABLE_NAME = "Transaction";
    private static final String TRANSACTION_DATETIME_COLUMN = "transactionDateTime";
    private static final String AMOUNT_COLUMN = "amount";
    private static final String TRANSACTION_TYPE_COLUMN = "transactionType";
    private static final String REASON_COLUMN = "reason";
    private static final String EFFECT_DATE_COLUMN = "effectDate";
    private static final String STATUS_COLUMN = "status";
    private static final String ACCOUNT_ID_COLUMN = "accountID";

    public void createTransaction(LocalDateTime transactionDateTime, double amount, String transactionType, String reason, LocalDateTime effectDate, String status) {
        String query = "INSERT INTO " + TRANSACTION_TABLE_NAME + " (" + TRANSACTION_DATETIME_COLUMN + ", " + AMOUNT_COLUMN + ", " +
                TRANSACTION_TYPE_COLUMN + ", " + REASON_COLUMN + ", " + EFFECT_DATE_COLUMN + ", " + STATUS_COLUMN + ") " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = PostgresqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(transactionDateTime));
            preparedStatement.setDouble(2, amount);
            preparedStatement.setString(3, transactionType);
            preparedStatement.setString(4, reason);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(effectDate));
            preparedStatement.setString(6, status);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM " + TRANSACTION_TABLE_NAME;
        try (Connection connection = PostgresqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = mapResultSetToTransaction(resultSet);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<Transaction> getTransactionsInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM " + TRANSACTION_TABLE_NAME + " WHERE " + EFFECT_DATE_COLUMN + " BETWEEN ? AND ?";
        try (Connection connection = PostgresqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(startDate));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(endDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = mapResultSetToTransaction(resultSet);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private Transaction mapResultSetToTransaction(ResultSet resultSet) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setTransactionDateTime(resultSet.getTimestamp(TRANSACTION_DATETIME_COLUMN).toLocalDateTime());
        transaction.setAmount(resultSet.getDouble(AMOUNT_COLUMN));
        transaction.setTransactionType(resultSet.getString(TRANSACTION_TYPE_COLUMN));
        transaction.setReason(resultSet.getString(REASON_COLUMN));
        transaction.setEffectDate(resultSet.getTimestamp(EFFECT_DATE_COLUMN).toLocalDateTime());
        transaction.setStatus(resultSet.getString(STATUS_COLUMN));
        return transaction;
    }

    public void createDepositTransaction(double amount, String reason, LocalDateTime effectDate, Long accountId) {
        String query = "INSERT INTO " + TRANSACTION_TABLE_NAME + " (" + TRANSACTION_DATETIME_COLUMN + ", " + AMOUNT_COLUMN + ", " +
                TRANSACTION_TYPE_COLUMN + ", " + REASON_COLUMN + ", " + EFFECT_DATE_COLUMN + ", " + STATUS_COLUMN + ", " + ACCOUNT_ID_COLUMN + ") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = PostgresqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setDouble(2, amount);
            preparedStatement.setString(3, "deposit");
            preparedStatement.setString(4, reason);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(effectDate));
            preparedStatement.setString(6, "completed");
            preparedStatement.setLong(7, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
