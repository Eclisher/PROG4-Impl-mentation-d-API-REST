package com.bank.implementation.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class DepositRequest {
    @JsonProperty("amount")
    private BigDecimal amount;

    public DepositRequest() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
