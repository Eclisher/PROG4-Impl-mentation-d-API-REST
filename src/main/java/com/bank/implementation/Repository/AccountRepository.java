package com.bank.implementation.Repository;

import com.bank.implementation.Model.Account;
import com.bank.implementation.PostgresqlConnection;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepository {

    public Account save(Account account) {
        String query = "INSERT INTO Account (accountNumber, clientLastName, clientFirstName, password, " +
                "clientDateOfBirth, monthlyNetSalary, creationDate, modificationDate, overdraftEnabled, " +
                "overdraftLimit, interestRateInitial, interestRateSubsequent, maxOverdraftDays) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = PostgresqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, account.getAccountNumber());
            preparedStatement.setString(2, account.getClientLastName());
            preparedStatement.setString(3, account.getClientFirstName());
            preparedStatement.setString(4, account.getPassword());
            if (account.getClientDateOfBirth() != null) {
                preparedStatement.setDate(5, new java.sql.Date(account.getClientDateOfBirth().getTime()));
            } else {
                preparedStatement.setNull(5, Types.DATE);
            }
            preparedStatement.setBigDecimal(6, account.getMonthlyNetSalary());
            preparedStatement.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.setBoolean(9, account.isOverdraftEnabled());
            preparedStatement.setBigDecimal(10, account.getOverdraftLimit());
            preparedStatement.setBigDecimal(11, account.getInterestRateInitial());
            preparedStatement.setBigDecimal(12, account.getInterestRateSubsequent());
            preparedStatement.setInt(13, account.getMaxOverdraftDays());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    int accountId = rs.getInt(1);
                    account.setAccountID(accountId);
                    return account;
                }
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM Account";
        try (Connection connection = PostgresqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setAccountID(resultSet.getInt("accountid"));
                account.setAccountNumber(resultSet.getString("accountnumber"));
                account.setClientLastName(resultSet.getString("clientlastname"));
                account.setClientFirstName(resultSet.getString("clientfirstname"));
                account.setPassword(resultSet.getString("password"));
                account.setClientDateOfBirth(resultSet.getDate("clientdateofbirth"));
                account.setMonthlyNetSalary(resultSet.getBigDecimal("monthlynetsalary"));
                account.setCreationDate(resultSet.getTimestamp("creationdate"));
                account.setModificationDate(resultSet.getTimestamp("modificationdate"));
                account.setOverdraftEnabled(resultSet.getBoolean("overdraftenabled"));
                account.setOverdraftLimit(resultSet.getBigDecimal("overdraftlimit"));
                account.setInterestRateInitial(resultSet.getBigDecimal("interestrateinitial"));
                account.setInterestRateSubsequent(resultSet.getBigDecimal("interestratesubsequent"));
                account.setMaxOverdraftDays(resultSet.getInt("maxoverdraftdays"));

                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }
    public List<Account> findByAccountNumberAndPassword(String accountNumber, String password) {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM Account WHERE accountNumber = ? AND password = ?";
        try (Connection connection = PostgresqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, accountNumber);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

}
