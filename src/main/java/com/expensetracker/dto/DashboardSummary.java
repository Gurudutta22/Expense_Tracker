package com.expensetracker.dto;

import com.expensetracker.model.Category;
import java.math.BigDecimal;
import java.util.List;

public class DashboardSummary {
    private String month;
    private BigDecimal spentThisMonth;
    private BigDecimal totalAllTime;
    private long transactionCount;
    private Category topCategory;     // highest-spend category this month (null if none)
    private BigDecimal totalBudget;   // null if no budgets set
    private BigDecimal budgetRemaining;
    private List<CategorySummary> categories;

    public DashboardSummary(String month, BigDecimal spentThisMonth, BigDecimal totalAllTime,
                            long transactionCount, Category topCategory, BigDecimal totalBudget,
                            BigDecimal budgetRemaining, List<CategorySummary> categories) {
        this.month = month;
        this.spentThisMonth = spentThisMonth;
        this.totalAllTime = totalAllTime;
        this.transactionCount = transactionCount;
        this.topCategory = topCategory;
        this.totalBudget = totalBudget;
        this.budgetRemaining = budgetRemaining;
        this.categories = categories;
    }
    public String getMonth() { return month; }
    public BigDecimal getSpentThisMonth() { return spentThisMonth; }
    public BigDecimal getTotalAllTime() { return totalAllTime; }
    public long getTransactionCount() { return transactionCount; }
    public Category getTopCategory() { return topCategory; }
    public BigDecimal getTotalBudget() { return totalBudget; }
    public BigDecimal getBudgetRemaining() { return budgetRemaining; }
    public List<CategorySummary> getCategories() { return categories; }
}
