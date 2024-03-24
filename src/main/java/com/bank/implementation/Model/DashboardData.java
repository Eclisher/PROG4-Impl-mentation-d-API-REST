package com.bank.implementation.Model;

import java.math.BigDecimal;
import java.util.Map;

public class DashboardData {
    private Map<String, BigDecimal> categoryAmounts;
    private BigDecimal totalExpenses;
    private BigDecimal totalIncome;

    public DashboardData() {
        this.categoryAmounts = categoryAmounts;
        this.totalExpenses = totalExpenses;
        this.totalIncome = totalIncome;
    }

    public Map<String, BigDecimal> getCategoryAmounts() {
        return categoryAmounts;
    }

    public void setCategoryAmounts(Map<String, BigDecimal> categoryAmounts) {
        this.categoryAmounts = categoryAmounts;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }
}

