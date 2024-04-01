package com.bank.implementation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresqlConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/banks";
    private static final String USERNAME = "*Your_Db_Username*";
    private static final String PASSWORD = "*Your_Db_Password*";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found", e);
        }
    }
}

