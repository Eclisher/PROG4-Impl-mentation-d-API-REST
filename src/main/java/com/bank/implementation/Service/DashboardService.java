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

        // Exemple 1 : Somme des montants par catégorie dans une période donnée
        Map<String, BigDecimal> categoryAmounts = transactionService.getCategoryAmountsInPeriod(LocalDate.now().minusMonths(1).atStartOfDay(), LocalDate.now().atStartOfDay());
        dashboardData.setCategoryAmounts(categoryAmounts);

        // Exemple 2 : Somme des dépenses dans une période donnée
        BigDecimal totalExpenses = transactionService.getTotalExpensesInPeriod(LocalDate.now().minusMonths(1).atStartOfDay(), LocalDate.now().atStartOfDay());
        dashboardData.setTotalExpenses(totalExpenses);

        // Exemple 3 : Somme des rentrées d'argent dans une période donnée
        BigDecimal totalIncome = transactionService.getTotalIncomeInPeriod(LocalDate.now().minusMonths(1).atStartOfDay(), LocalDate.now().atStartOfDay());
        dashboardData.setTotalIncome(totalIncome);

        return dashboardData;
    }
}
