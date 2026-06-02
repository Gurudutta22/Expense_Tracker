package com.expensetracker.config;

import com.expensetracker.model.Budget;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.repository.BudgetRepository;
import com.expensetracker.repository.ExpenseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Seeds sample expenses and budgets on first run so the dashboard isn't empty. */
@Component
public class DataInitializer implements CommandLineRunner {

    private final ExpenseRepository expenseRepo;
    private final BudgetRepository budgetRepo;

    public DataInitializer(ExpenseRepository expenseRepo, BudgetRepository budgetRepo) {
        this.expenseRepo = expenseRepo;
        this.budgetRepo = budgetRepo;
    }

    @Override
    public void run(String... args) {
        if (expenseRepo.count() > 0) return;

        LocalDate today = LocalDate.now();
        LocalDate first = today.withDayOfMonth(1);

        add("Groceries at supermarket", "1850.00", Category.FOOD, today, first);
        add("Coffee", "250.00", Category.FOOD, today, first);
        add("Lunch with friends", "850.00", Category.FOOD, today.minusDays(3), first);
        add("Auto rickshaw", "120.00", Category.TRANSPORT, today.minusDays(1), first);
        add("Metro card recharge", "500.00", Category.TRANSPORT, today.minusDays(5), first);
        add("Movie tickets", "600.00", Category.ENTERTAINMENT, today.minusDays(2), first);
        add("Music subscription", "199.00", Category.ENTERTAINMENT, today.minusDays(6), first);
        add("Electricity bill", "2400.00", Category.BILLS, today.minusDays(3), first);
        add("Internet bill", "799.00", Category.BILLS, today.minusDays(8), first);
        add("New headphones", "3200.00", Category.SHOPPING, today.minusDays(4), first);
        add("T-shirt", "999.00", Category.SHOPPING, today.minusDays(9), first);
        add("Pharmacy", "430.00", Category.HEALTH, today.minusDays(7), first);

        budget(Category.FOOD, "6000");
        budget(Category.TRANSPORT, "2000");
        budget(Category.SHOPPING, "4000");
        budget(Category.BILLS, "4000");
        budget(Category.ENTERTAINMENT, "2000");
        budget(Category.HEALTH, "3000");
    }

    private void add(String desc, String amount, Category cat, LocalDate date, LocalDate floor) {
        if (date.isBefore(floor)) date = floor;   // keep seed data within the current month
        expenseRepo.save(new Expense(desc, new BigDecimal(amount), cat, date));
    }

    private void budget(Category cat, String limit) {
        budgetRepo.save(new Budget(cat, new BigDecimal(limit)));
    }
}
