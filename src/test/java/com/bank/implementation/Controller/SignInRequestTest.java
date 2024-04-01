package com.bank.implementation.Controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignInRequestTest {

    @Test
    public void testSignInRequestGettersAndSetters() {
        // create SigninRequest instances
        SignInRequest signInRequest = new SignInRequest();

        // create values to check
        String accountNumber = "123456789";
        String password = "password123";
        signInRequest.setAccountNumber(accountNumber);
        signInRequest.setPassword(password);

        // check all getter and return the true values
        assertEquals(accountNumber, signInRequest.getAccountNumber());
        assertEquals(password, signInRequest.getPassword());
    }
}
