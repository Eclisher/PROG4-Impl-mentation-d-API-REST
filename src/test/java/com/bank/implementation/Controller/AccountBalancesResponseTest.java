package com.bank.implementation.Controller;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountBalancesResponseTest {

    @Test
    public void testAccountBalancesResponseConstructor() {
        BigDecimal principalBalance = new BigDecimal("1000.00");
        BigDecimal loanAmount = new BigDecimal("5000.00");
        BigDecimal loanInterest = new BigDecimal("250.00");


        AccountBalancesResponse response = new AccountBalancesResponse(principalBalance, loanAmount, loanInterest);


        assertEquals(principalBalance, response.getPrincipalBalance());
        assertEquals(loanAmount, response.getLoanAmount());
        assertEquals(loanInterest, response.getLoanInterest());
    }

    @Test
    public void testAccountBalancesResponseSetters() {
        BigDecimal principalBalance = new BigDecimal("1000.00");
        BigDecimal loanAmount = new BigDecimal("5000.00");
        BigDecimal loanInterest = new BigDecimal("250.00");


        AccountBalancesResponse response = new AccountBalancesResponse();


        response.setPrincipalBalance(principalBalance);
        response.setLoanAmount(loanAmount);
        response.setLoanInterest(loanInterest);


        assertEquals(principalBalance, response.getPrincipalBalance());
        assertEquals(loanAmount, response.getLoanAmount());
        assertEquals(loanInterest, response.getLoanInterest());
    }

    @Test
    public void testAccountBalancesResponseDefaultConstructor() {

        AccountBalancesResponse response = new AccountBalancesResponse();


        assertEquals(null, response.getPrincipalBalance());
        assertEquals(null, response.getLoanAmount());
        assertEquals(null, response.getLoanInterest());
    }

    @Test
    public void testAccountBalancesResponseEquals() {
        BigDecimal principalBalance1 = new BigDecimal("1000.00");
        BigDecimal loanAmount1 = new BigDecimal("5000.00");
        BigDecimal loanInterest1 = new BigDecimal("250.00");

        BigDecimal principalBalance2 = new BigDecimal("1000.00");
        BigDecimal loanAmount2 = new BigDecimal("5000.00");
        BigDecimal loanInterest2 = new BigDecimal("250.00");


        AccountBalancesResponse response1 = new AccountBalancesResponse(principalBalance1, loanAmount1, loanInterest1);
        AccountBalancesResponse response2 = new AccountBalancesResponse(principalBalance2, loanAmount2, loanInterest2);


        assertEquals(response1, response2);
    }
}
