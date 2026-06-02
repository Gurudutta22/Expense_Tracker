package com.expensetracker.dto;

import com.expensetracker.model.Category;
import java.math.BigDecimal;

public class CategorySummary {
    private Category category;
    private BigDecimal spent;
    private BigDecimal budget;   // null if no budget set
    private Integer percent;     // spent as % of budget; null if no budget
    private boolean over;        // true if spent > budget

    public CategorySummary(Category category, BigDecimal spent, BigDecimal budget, Integer percent, boolean over) {
        this.category = category;
        this.spent = spent;
        this.budget = budget;
        this.percent = percent;
        this.over = over;
    }
    public Category getCategory() { return category; }
    public BigDecimal getSpent() { return spent; }
    public BigDecimal getBudget() { return budget; }
    public Integer getPercent() { return percent; }
    public boolean isOver() { return over; }
}
