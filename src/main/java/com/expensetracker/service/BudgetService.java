package com.expensetracker.service;

import com.expensetracker.dto.BudgetRequest;
import com.expensetracker.model.Budget;
import com.expensetracker.repository.BudgetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepo;

    public BudgetService(BudgetRepository budgetRepo) {
        this.budgetRepo = budgetRepo;
    }

    @Transactional
    public Budget set(BudgetRequest req) {
        if (req.getCategory() == null)
            throw new IllegalArgumentException("Category is required");
        if (req.getMonthlyLimit() == null || req.getMonthlyLimit().signum() < 0)
            throw new IllegalArgumentException("Monthly limit must be zero or more");

        Budget budget = budgetRepo.findByCategory(req.getCategory())
                .orElseGet(() -> new Budget(req.getCategory(), req.getMonthlyLimit()));
        budget.setMonthlyLimit(req.getMonthlyLimit());
        return budgetRepo.save(budget);
    }

    public List<Budget> all() {
        return budgetRepo.findAll();
    }
}
