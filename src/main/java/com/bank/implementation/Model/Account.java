package com.bank.implementation.Model;

import java.math.BigDecimal;
import java.util.Date;


public class Account {
    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setClientDateOfBirth(Date clientDateOfBirth) {
        this.clientDateOfBirth = clientDateOfBirth;
    }

    public void setMonthlyNetSalary(BigDecimal monthlyNetSalary) {
        this.monthlyNetSalary = monthlyNetSalary;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public void setOverdraftEnabled(boolean overdraftEnabled) {
        this.overdraftEnabled = overdraftEnabled;
    }

    public void setOverdraftLimit(BigDecimal overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    public void setInterestRateInitial(BigDecimal interestRateInitial) {
        this.interestRateInitial = interestRateInitial;
    }

    public void setInterestRateSubsequent(BigDecimal interestRateSubsequent) {
        this.interestRateSubsequent = interestRateSubsequent;
    }

    public void setMaxOverdraftDays(int maxOverdraftDays) {
        this.maxOverdraftDays = maxOverdraftDays;
    }

    private int accountID;
    private String accountNumber;
    private String clientLastName;

    public Account() {
        this.accountID = accountID;
        this.accountNumber = accountNumber;
        this.clientLastName = clientLastName;
        this.clientFirstName = clientFirstName;
        this.password = password;
        this.clientDateOfBirth = clientDateOfBirth;
        this.monthlyNetSalary = monthlyNetSalary;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.overdraftEnabled = overdraftEnabled;
        this.overdraftLimit = overdraftLimit;
        this.interestRateInitial = interestRateInitial;
        this.interestRateSubsequent = interestRateSubsequent;
        this.maxOverdraftDays = maxOverdraftDays;
    }

    public int getAccountID() {
        return accountID;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public String getPassword() {
        return password;
    }

    public Date getClientDateOfBirth() {
        return clientDateOfBirth;
    }

    public BigDecimal getMonthlyNetSalary() {
        return monthlyNetSalary;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public boolean isOverdraftEnabled() {
        return overdraftEnabled;
    }

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }

    public BigDecimal getInterestRateInitial() {
        return interestRateInitial;
    }

    public BigDecimal getInterestRateSubsequent() {
        return interestRateSubsequent;
    }

    public int getMaxOverdraftDays() {
        return maxOverdraftDays;
    }

    private String clientFirstName;
    private String password;
    private Date clientDateOfBirth;
    private BigDecimal monthlyNetSalary;
    private Date creationDate;
    private Date modificationDate;
    private boolean overdraftEnabled;
    private BigDecimal overdraftLimit;
    private BigDecimal interestRateInitial;
    private BigDecimal interestRateSubsequent;
    private int maxOverdraftDays;
}
