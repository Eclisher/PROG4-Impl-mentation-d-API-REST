package com.bank.implementation.Service;

import com.bank.implementation.Model.DashboardData;
import com.bank.implementation.Service.DashboardService;
import com.bank.implementation.Service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DashboardServiceTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private AccountService accountService;

    @Test
    void generateDashboardData() {
        // Mock data for category amounts
        Map<String, BigDecimal> categoryAmounts = new HashMap<>();
        categoryAmounts.put("Groceries", new BigDecimal("150.00"));
        categoryAmounts.put("Shopping", new BigDecimal("200.00"));

        // Mock data for total expenses and income
        BigDecimal totalExpenses = new BigDecimal("500.00");
        BigDecimal totalIncome = new BigDecimal("1000.00");

        // Set up mock behavior for TransactionService
        when(transactionService.getCategoryAmountsInPeriod(any(), any())).thenReturn(categoryAmounts);
        when(transactionService.getTotalExpensesInPeriod(any(), any())).thenReturn(totalExpenses);
        when(transactionService.getTotalIncomeInPeriod(any(), any())).thenReturn(totalIncome);

        // Create the DashboardService instance to test
        DashboardService dashboardService = new DashboardService(transactionService, accountService);

        // Call the method to generate dashboard data
        DashboardData dashboardData = dashboardService.generateDashboardData();

        // Assertions
        assertEquals(categoryAmounts, dashboardData.getCategoryAmounts());
        assertEquals(totalExpenses, dashboardData.getTotalExpenses());
        assertEquals(totalIncome, dashboardData.getTotalIncome());

        // Verify that the methods of TransactionService were called
        verify(transactionService, times(1)).getCategoryAmountsInPeriod(any(), any());
        verify(transactionService, times(1)).getTotalExpensesInPeriod(any(), any());
        verify(transactionService, times(1)).getTotalIncomeInPeriod(any(), any());
    }
}
