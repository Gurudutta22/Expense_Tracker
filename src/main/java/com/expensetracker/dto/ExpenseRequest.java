package com.expensetracker.dto;

import com.expensetracker.model.Category;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseRequest {
    private String description;
    private BigDecimal amount;
    private Category category;
    private LocalDate spentOn;

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public LocalDate getSpentOn() { return spentOn; }
    public void setSpentOn(LocalDate spentOn) { this.spentOn = spentOn; }
}
