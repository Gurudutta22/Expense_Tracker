package com.expensetracker.web;

import com.expensetracker.dto.BudgetRequest;
import com.expensetracker.dto.DashboardSummary;
import com.expensetracker.dto.ExpenseRequest;
import com.expensetracker.model.Budget;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.service.BudgetService;
import com.expensetracker.service.DashboardService;
import com.expensetracker.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final BudgetService budgetService;
    private final DashboardService dashboardService;

    public ExpenseController(ExpenseService expenseService, BudgetService budgetService,
                             DashboardService dashboardService) {
        this.expenseService = expenseService;
        this.budgetService = budgetService;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/categories")
    public Category[] categories() { return Category.values(); }

    @GetMapping("/expenses")
    public List<Expense> expenses() { return expenseService.recent(); }

    @PostMapping("/expenses")
    public Expense addExpense(@RequestBody ExpenseRequest req) { return expenseService.add(req); }

    @DeleteMapping("/expenses/{id}")
    public Map<String, Object> deleteExpense(@PathVariable Long id) {
        expenseService.delete(id);
        return Map.of("deleted", true, "id", id);
    }

    @GetMapping("/dashboard")
    public DashboardSummary dashboard() { return dashboardService.summary(); }

    @GetMapping("/budgets")
    public List<Budget> budgets() { return budgetService.all(); }

    @PostMapping("/budgets")
    public Budget setBudget(@RequestBody BudgetRequest req) { return budgetService.set(req); }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
}
