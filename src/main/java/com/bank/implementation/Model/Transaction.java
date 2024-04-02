package com.bank.implementation.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private Long transactionId;
    private LocalDateTime transactionDateTime;
    private BigDecimal amount;
    private String transactionType;
    private String reason;
    private LocalDateTime effectDate;
    private LocalDateTime registrationDate;
    private String status;
    private Long accountId;
    private  Long categoryId;
    public Transaction(Long transactionId, LocalDateTime transactionDateTime, BigDecimal amount, String transactionType, String reason, LocalDateTime effectDate, LocalDateTime registrationDate, String status, Long accountId, Long categoryId) {
        this.transactionId = transactionId;
        this.transactionDateTime = transactionDateTime;
        this.amount = amount;
        this.transactionType = transactionType;
        this.reason = reason;
        this.effectDate = effectDate;
        this.registrationDate = registrationDate;
        this.status = status;
        this.accountId = accountId;
        this.categoryId = categoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }


    public Transaction() {

    }


    public LocalDateTime getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(LocalDateTime transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(LocalDateTime effectDate) {
        this.effectDate = effectDate;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

