package com.bank.implementation.Service;

import com.bank.implementation.Model.DashboardData;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Service
public class DashboardService {

    private final TransactionService transactionService;
    private final AccountService accountService;

    public DashboardService(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    public DashboardData generateDashboardData() {
        DashboardData dashboardData = new DashboardData();

        // Example 1: Sum of amounts by category for a given period
        Map<String, BigDecimal> categoryAmounts = transactionService.getCategoryAmountsInPeriod(LocalDate.now().minusMonths(1).atStartOfDay(), LocalDate.now().atStartOfDay());
        dashboardData.setCategoryAmounts(categoryAmounts);

        // Example 2: Sum of expenses in a given period
        BigDecimal totalExpenses = transactionService.getTotalExpensesInPeriod(LocalDate.now().minusMonths(1).atStartOfDay(), LocalDate.now().atStartOfDay());
        dashboardData.setTotalExpenses(totalExpenses);

        // Example 3: Sum of cash inflows over a given period
        BigDecimal totalIncome = transactionService.getTotalIncomeInPeriod(LocalDate.now().minusMonths(1).atStartOfDay(), LocalDate.now().atStartOfDay());
        dashboardData.setTotalIncome(totalIncome);

        return dashboardData;
    }
}
