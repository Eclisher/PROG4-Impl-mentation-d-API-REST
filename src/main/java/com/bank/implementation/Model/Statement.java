package com.bank.implementation.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Statement {
    private int statementID;
    private LocalDateTime transactionDateTime;
    private String reference;
    private String transactionType;

    public int getStatementID() {
        return statementID;
    }

    public void setStatementID(int statementID) {
        this.statementID = statementID;
    }

    public LocalDateTime getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(LocalDateTime transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPrincipalBalance() {
        return principalBalance;
    }

    public void setPrincipalBalance(BigDecimal principalBalance) {
        this.principalBalance = principalBalance;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public Statement(int statementID, LocalDateTime transactionDateTime, String reference, String transactionType, BigDecimal amount, BigDecimal principalBalance, int accountID) {
        this.statementID = statementID;
        this.transactionDateTime = transactionDateTime;
        this.reference = reference;
        this.transactionType = transactionType;
        this.amount = amount;
        this.principalBalance = principalBalance;
        this.accountID = accountID;
    }

    private BigDecimal amount;
    private BigDecimal principalBalance;
    private int accountID;

}
