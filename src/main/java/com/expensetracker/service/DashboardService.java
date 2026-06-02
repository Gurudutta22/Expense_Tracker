package com.expensetracker.service;

import com.expensetracker.dto.CategorySummary;
import com.expensetracker.dto.CategoryTotal;
import com.expensetracker.dto.DashboardSummary;
import com.expensetracker.model.Budget;
import com.expensetracker.model.Category;
import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

/**
 * Builds the dashboard: month-to-date total, per-category breakdown (SQL GROUP BY),
 * budget comparison, and the top category. Categories are sorted by spend (a simple sort/DSA step).
 */
@Service
public class DashboardService {

    private final ExpenseRepository expenseRepo;
    private final BudgetRepository budgetRepo;

    public DashboardService(ExpenseRepository expenseRepo, BudgetRepository budgetRepo) {
        this.expenseRepo = expenseRepo;
        this.budgetRepo = budgetRepo;
    }

    public DashboardSummary summary() {
        LocalDate today = LocalDate.now();
        LocalDate first = today.withDayOfMonth(1);

        BigDecimal spentMonth = expenseRepo.sumBetween(first, today);
        BigDecimal allTime = expenseRepo.sumAll();
        long count = expenseRepo.countBySpentOnBetween(first, today);

        Map<Category, BigDecimal> spentByCat = new HashMap<>();
        for (CategoryTotal ct : expenseRepo.totalsByCategoryBetween(first, today))
            spentByCat.put(ct.getCategory(), ct.getTotal());

        Map<Category, BigDecimal> limits = new HashMap<>();
        for (Budget b : budgetRepo.findAll())
            limits.put(b.getCategory(), b.getMonthlyLimit());

        Set<Category> cats = new LinkedHashSet<>();
        cats.addAll(spentByCat.keySet());
        cats.addAll(limits.keySet());

        List<CategorySummary> list = new ArrayList<>();
        for (Category c : cats) {
            BigDecimal spent = spentByCat.getOrDefault(c, BigDecimal.ZERO);
            BigDecimal limit = limits.get(c);
            Integer percent = null;
            boolean over = false;
            if (limit != null && limit.signum() > 0) {
                percent = spent.multiply(BigDecimal.valueOf(100))
                               .divide(limit, 0, RoundingMode.HALF_UP).intValue();
                over = spent.compareTo(limit) > 0;
            }
            list.add(new CategorySummary(c, spent, limit, percent, over));
        }
        // sort by amount spent, highest first
        list.sort((a, b) -> b.getSpent().compareTo(a.getSpent()));

        Category top = (!list.isEmpty() && list.get(0).getSpent().signum() > 0)
                ? list.get(0).getCategory() : null;

        BigDecimal totalBudget = limits.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal remaining = totalBudget.signum() > 0 ? totalBudget.subtract(spentMonth) : null;
        String monthLabel = today.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + today.getYear();

        return new DashboardSummary(monthLabel, spentMonth, allTime, count, top,
                totalBudget.signum() > 0 ? totalBudget : null, remaining, list);
    }
}
