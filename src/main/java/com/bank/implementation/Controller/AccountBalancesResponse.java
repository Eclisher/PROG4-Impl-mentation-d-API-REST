package com.bank.implementation.Controller;

import java.math.BigDecimal;

public class AccountBalancesResponse {
    private BigDecimal principalBalance;
    private BigDecimal loanAmount;
    private BigDecimal loanInterest;

    public AccountBalancesResponse(BigDecimal principalBalance, BigDecimal loanAmount, BigDecimal loanInterest) {
        this.principalBalance = principalBalance;
        this.loanAmount = loanAmount;
        this.loanInterest = loanInterest;
    }

    public AccountBalancesResponse() {

    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getLoanInterest() {
        return loanInterest;
    }

    public void setLoanInterest(BigDecimal loanInterest) {
        this.loanInterest = loanInterest;
    }

    public BigDecimal getPrincipalBalance() {
        return principalBalance;
    }

    public void setPrincipalBalance(BigDecimal principalBalance) {
        this.principalBalance = principalBalance;
    }
}
