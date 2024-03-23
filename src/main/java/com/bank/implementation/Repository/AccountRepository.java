package com.bank.implementation.Repository;

import com.bank.implementation.Model.Account;
import com.bank.implementation.PostgresqlConnection;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepository {
    private static final String ACCOUNTID_COLUMN = "accountid";
    private static final String ACCOUNTNUMBER_COLUMN = "accountnumber";
    private static final String CLIENTLASTNAME_COLUMN = "clientlastname";
    private static final String CLIENTFIRSTNAME_COLUMN = "clientfirstname";
    private static final String PASSWORD_COLUMN = "password";
    private static final String CLIENTDATEOFBIRTH_COLUMN = "clientdateofbirth";
    private static final String MONTHLYNETSALARY_COLUMN = "monthlynetsalary";
    private static final String CREATIONDATE_COLUMN = "creationdate";
    private static final String MODIFICATIONDATE_COLUMN = "modificationdate";
    private static final String OVERDRAFTENABLED_COLUMN = "overdraftenabled";
    private static final String OVERDRAFTLIMIT_COLUMN = "overdraftlimit";
    private static final String INTERESTRATEINITIAL_COLUMN = "interestrateinitial";
    private static final String INTERESTRATESUBSEQUENT_COLUMN = "interestratesubsequent";
    private static final String MAXOVERDRAFTDAYS_COLUMN = "maxoverdraftdays";

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
                account.setAccountID(resultSet.getInt(ACCOUNTID_COLUMN));
                account.setAccountNumber(resultSet.getString(ACCOUNTNUMBER_COLUMN));
                account.setClientLastName(resultSet.getString(CLIENTLASTNAME_COLUMN));
                account.setClientFirstName(resultSet.getString(CLIENTFIRSTNAME_COLUMN));
                account.setPassword(resultSet.getString(PASSWORD_COLUMN));
                account.setClientDateOfBirth(resultSet.getDate(CLIENTDATEOFBIRTH_COLUMN));
                account.setMonthlyNetSalary(resultSet.getBigDecimal(MONTHLYNETSALARY_COLUMN));
                account.setCreationDate(resultSet.getTimestamp(CREATIONDATE_COLUMN));
                account.setModificationDate(resultSet.getTimestamp(MODIFICATIONDATE_COLUMN));
                account.setOverdraftEnabled(resultSet.getBoolean(OVERDRAFTENABLED_COLUMN));
                account.setOverdraftLimit(resultSet.getBigDecimal(OVERDRAFTLIMIT_COLUMN));
                account.setInterestRateInitial(resultSet.getBigDecimal(INTERESTRATEINITIAL_COLUMN));
                account.setInterestRateSubsequent(resultSet.getBigDecimal(INTERESTRATESUBSEQUENT_COLUMN));
                account.setMaxOverdraftDays(resultSet.getInt(MAXOVERDRAFTDAYS_COLUMN));

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

    public Account findById(Long accountId) {
        String query = "SELECT * FROM Account WHERE accountId = ?";
        try (Connection connection = PostgresqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Account account = new Account();
                account.setAccountID(resultSet.getInt(ACCOUNTID_COLUMN));
                account.setAccountNumber(resultSet.getString(ACCOUNTNUMBER_COLUMN));
                account.setClientLastName(resultSet.getString(CLIENTLASTNAME_COLUMN));
                account.setClientFirstName(resultSet.getString(CLIENTFIRSTNAME_COLUMN));
                account.setPassword(resultSet.getString(PASSWORD_COLUMN));
                account.setClientDateOfBirth(resultSet.getDate(CLIENTDATEOFBIRTH_COLUMN));
                account.setMonthlyNetSalary(resultSet.getBigDecimal(MONTHLYNETSALARY_COLUMN));
                account.setCreationDate(resultSet.getTimestamp(CREATIONDATE_COLUMN));
                account.setModificationDate(resultSet.getTimestamp(MODIFICATIONDATE_COLUMN));
                account.setOverdraftEnabled(resultSet.getBoolean(OVERDRAFTENABLED_COLUMN));
                account.setOverdraftLimit(resultSet.getBigDecimal(OVERDRAFTLIMIT_COLUMN));
                account.setInterestRateInitial(resultSet.getBigDecimal(INTERESTRATEINITIAL_COLUMN));
                account.setInterestRateSubsequent(resultSet.getBigDecimal(INTERESTRATESUBSEQUENT_COLUMN));
                account.setMaxOverdraftDays(resultSet.getInt(MAXOVERDRAFTDAYS_COLUMN));
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
