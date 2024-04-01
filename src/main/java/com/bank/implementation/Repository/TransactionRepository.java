package com.bank.implementation.Repository;

import com.bank.implementation.Model.Transaction;
import com.bank.implementation.PostgresqlConnection;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final String TRANSACTION_ID_COLUMN = "transactionID";
    private static final String CATEGORY_ID_COLUMN ="categoryID" ;

    public void createTransaction(LocalDateTime transactionDateTime, double amount, String transactionType, String reason, LocalDateTime effectDate, String status, Long accountId, Long categoryId) {
        String query = "INSERT INTO " + TRANSACTION_TABLE_NAME + " (" + TRANSACTION_DATETIME_COLUMN + ", " + AMOUNT_COLUMN + ", " +
                TRANSACTION_TYPE_COLUMN + ", " + REASON_COLUMN + ", " + EFFECT_DATE_COLUMN + ", " + STATUS_COLUMN + ", " + ACCOUNT_ID_COLUMN + ", " + CATEGORY_ID_COLUMN + ") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = PostgresqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(transactionDateTime));
            preparedStatement.setDouble(2, amount);
            preparedStatement.setString(3, transactionType);
            preparedStatement.setString(4, reason);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(effectDate));
            preparedStatement.setString(6, status);
            preparedStatement.setLong(7, accountId);
            preparedStatement.setLong(8, categoryId);
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


    public void createDepositTransaction(double amount, String reason, LocalDateTime effectDate, Long accountId) {
        String query = "INSERT INTO " + TRANSACTION_TABLE_NAME + " (" + TRANSACTION_DATETIME_COLUMN + ", " + AMOUNT_COLUMN + ", " +
                TRANSACTION_TYPE_COLUMN + ", " + REASON_COLUMN + ", " + EFFECT_DATE_COLUMN + ", " + STATUS_COLUMN + ", " + ACCOUNT_ID_COLUMN + ", " + TRANSACTION_ID_COLUMN +" ) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
    public void updateTransactionCategory(Long transactionId, Long categoryId) {
            String query = "UPDATE " + TRANSACTION_TABLE_NAME + " SET categoryId = ? WHERE id = ?";
            try (Connection connection = PostgresqlConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setLong(1, categoryId);
                preparedStatement.setLong(2, transactionId);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    public Map<String, BigDecimal> getCategoryAmountsInPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        Map<String, BigDecimal> categoryAmounts = new HashMap<>();
        String query = "SELECT category, SUM(amount) AS total_amount FROM transactions " +
                "WHERE transaction_date BETWEEN ? AND ? " +
                "GROUP BY category";
        try (Connection connection = PostgresqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(startDate));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(endDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String category = resultSet.getString("category");
                BigDecimal totalAmount = resultSet.getBigDecimal("total_amount");
                categoryAmounts.put(category, totalAmount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryAmounts;
    }

    public BigDecimal getTotalExpensesInPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal totalExpenses = BigDecimal.ZERO;
        String query = "SELECT SUM(amount) AS total_expenses FROM transactions " +
                "WHERE transaction_date BETWEEN ? AND ? AND transaction_type = 'expense'";
        try (Connection connection = PostgresqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(startDate));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(endDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalExpenses = resultSet.getBigDecimal("total_expenses");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalExpenses;
    }

    public BigDecimal getTotalIncomeInPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal totalIncome = BigDecimal.ZERO;
        String query = "SELECT SUM(amount) AS total_income FROM transactions " +
                "WHERE transaction_date BETWEEN ? AND ? AND transaction_type = 'income'";
        try (Connection connection = PostgresqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(startDate));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(endDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalIncome = resultSet.getBigDecimal("total_income");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalIncome;
    }
    private Transaction mapResultSetToTransaction(ResultSet resultSet) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(resultSet.getLong(TRANSACTION_ID_COLUMN));
        transaction.setTransactionDateTime(resultSet.getTimestamp(TRANSACTION_DATETIME_COLUMN).toLocalDateTime());
        transaction.setAmount(resultSet.getDouble(AMOUNT_COLUMN));
        transaction.setTransactionType(resultSet.getString(TRANSACTION_TYPE_COLUMN));
        transaction.setReason(resultSet.getString(REASON_COLUMN));
        transaction.setEffectDate(resultSet.getTimestamp(EFFECT_DATE_COLUMN).toLocalDateTime());
        transaction.setStatus(resultSet.getString(STATUS_COLUMN));
        transaction.setAccountId(resultSet.getLong(ACCOUNT_ID_COLUMN));
        transaction.setCategoryId(resultSet.getLong(CATEGORY_ID_COLUMN));
        return transaction;
    }
}
