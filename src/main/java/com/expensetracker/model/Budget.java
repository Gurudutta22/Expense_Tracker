package com.expensetracker.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private Category category;

    private BigDecimal monthlyLimit;

    public Budget() { }

    public Budget(Category category, BigDecimal monthlyLimit) {
        this.category = category;
        this.monthlyLimit = monthlyLimit;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public BigDecimal getMonthlyLimit() { return monthlyLimit; }
    public void setMonthlyLimit(BigDecimal monthlyLimit) { this.monthlyLimit = monthlyLimit; }
}
