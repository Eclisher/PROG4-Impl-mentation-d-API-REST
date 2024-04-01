package com.bank.implementation.Controller;

import com.bank.implementation.Controller.AccountController;
import com.bank.implementation.Model.Account;
import com.bank.implementation.Service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1L, "John Doe", BigDecimal.valueOf(1000), true, BigDecimal.valueOf(500)));
        accounts.add(new Account(2L, "Jane Smith", BigDecimal.valueOf(2000), false, BigDecimal.ZERO));

        when(accountService.getAllAccounts()).thenReturn(accounts);

        List<Account> result = accountController.getAllAccounts();

        assertEquals(accounts.size(), result.size());
        verify(accountService, times(1)).getAllAccounts();
    }

    @Test
    void testGetAccountById() {
        Account account = new Account(1L, "John Doe", BigDecimal.valueOf(1000), true, BigDecimal.valueOf(500));

        when(accountService.findById(1L)).thenReturn(Optional.of(account));

        ResponseEntity<Account> response = accountController.getAccountById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    // Ajoutez d'autres tests pour les autres méthodes de AccountController
}
