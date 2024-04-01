package com.bank.implementation.Controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WithdrawalRequest {
    private BigDecimal amount;
    private LocalDateTime dateTime;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public WithdrawalRequest(BigDecimal amount, LocalDateTime dateTime) {
        this.amount = amount;
        this.dateTime = dateTime;
    }
}
