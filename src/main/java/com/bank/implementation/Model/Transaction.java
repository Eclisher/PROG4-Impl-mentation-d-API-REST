package com.bank.implementation.Model;

import java.time.LocalDateTime;

public class Transaction {
    private LocalDateTime transactionDateTime;
    private double amount;
    private String transactionType;
    private String reason;
    private LocalDateTime effectDate;
    private LocalDateTime registrationDate;
    private String status;

    public Transaction( LocalDateTime transactionDateTime, double amount, String transactionType, String reason, LocalDateTime effectDate, LocalDateTime registrationDate, String status) {
        this.transactionDateTime = transactionDateTime;
        this.amount = amount;
        this.transactionType = transactionType;
        this.reason = reason;
        this.effectDate = effectDate;
        this.registrationDate = registrationDate;
        this.status = status;
    }


    public LocalDateTime getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(LocalDateTime transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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

