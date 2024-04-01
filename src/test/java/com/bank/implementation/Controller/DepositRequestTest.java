package com.bank.implementation.Controller;

import com.bank.implementation.Controller.DepositRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DepositRequestTest {

    @Test
    void testGetAmount() throws NoSuchFieldException, IllegalAccessException {
        BigDecimal amount = new BigDecimal("100.50");
        DepositRequest depositRequest = new DepositRequest();
        Field field = DepositRequest.class.getDeclaredField("amount");
        field.setAccessible(true);
        field.set(depositRequest, amount);

        assertEquals(amount, depositRequest.getAmount());
    }

    @Test
    void testSetAmount() throws NoSuchFieldException, IllegalAccessException {
        BigDecimal amount = new BigDecimal("150.75");
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAmount(amount);

        Field field = DepositRequest.class.getDeclaredField("amount");
        field.setAccessible(true);

        assertEquals(amount, field.get(depositRequest));
    }
}
