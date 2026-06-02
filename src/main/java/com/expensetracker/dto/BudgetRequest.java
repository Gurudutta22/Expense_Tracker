package com.expensetracker.dto;

import com.expensetracker.model.Category;
import java.math.BigDecimal;

public class BudgetRequest {
    private Category category;
    private BigDecimal monthlyLimit;

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public BigDecimal getMonthlyLimit() { return monthlyLimit; }
    public void setMonthlyLimit(BigDecimal monthlyLimit) { this.monthlyLimit = monthlyLimit; }
}
