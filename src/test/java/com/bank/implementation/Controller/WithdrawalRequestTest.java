package com.bank.implementation.Controller;

import com.bank.implementation.Controller.WithdrawalRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WithdrawalRequestTest {

    @Test
    void testGetAmount() {
        BigDecimal amount = new BigDecimal("100.50");
        WithdrawalRequest withdrawalRequest = new WithdrawalRequest(amount, LocalDateTime.now());

        assertEquals(amount, withdrawalRequest.getAmount());
    }

    @Test
    void testGetDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        WithdrawalRequest withdrawalRequest = new WithdrawalRequest(new BigDecimal("100.50"), dateTime);

        assertEquals(dateTime, withdrawalRequest.getDateTime());
    }
}
