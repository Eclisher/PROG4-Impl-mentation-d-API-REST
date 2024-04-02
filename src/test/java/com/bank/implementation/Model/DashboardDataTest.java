package com.bank.implementation.Model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashboardDataTest {

    @Test
    public void testDashboardDataConstructorAndGettersSetters() {

        DashboardData dashboardData = new DashboardData();


        Map<String, BigDecimal> categoryAmounts = new HashMap<>();
        categoryAmounts.put("Category1", BigDecimal.valueOf(100.00));
        categoryAmounts.put("Category2", BigDecimal.valueOf(200.00));
        BigDecimal totalExpenses = BigDecimal.valueOf(300.00);
        BigDecimal totalIncome = BigDecimal.valueOf(5000.00);

        dashboardData.setCategoryAmounts(categoryAmounts);
        dashboardData.setTotalExpenses(totalExpenses);
        dashboardData.setTotalIncome(totalIncome);


        assertEquals(categoryAmounts, dashboardData.getCategoryAmounts());
        assertEquals(totalExpenses, dashboardData.getTotalExpenses());
        assertEquals(totalIncome, dashboardData.getTotalIncome());
    }
}
