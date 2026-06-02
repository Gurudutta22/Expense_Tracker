package com.expensetracker.service;

import com.expensetracker.dto.ExpenseRequest;
import com.expensetracker.model.Expense;
import com.expensetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepo;
    private final AuditService auditService;

    public ExpenseService(ExpenseRepository expenseRepo, AuditService auditService) {
        this.expenseRepo = expenseRepo;
        this.auditService = auditService;
    }

    @Transactional
    public Expense add(ExpenseRequest req) {
        if (req.getAmount() == null || req.getAmount().signum() <= 0)
            throw new IllegalArgumentException("Amount must be greater than zero");
        if (req.getCategory() == null)
            throw new IllegalArgumentException("Category is required");

        String description = (req.getDescription() == null || req.getDescription().isBlank())
                ? "(no description)" : req.getDescription().trim();
        LocalDate date = (req.getSpentOn() == null) ? LocalDate.now() : req.getSpentOn();

        Expense saved = expenseRepo.save(new Expense(description, req.getAmount(), req.getCategory(), date));
        auditService.recordAsync("Added expense #" + saved.getId() + " " + saved.getCategory() + " " + saved.getAmount());
        return saved;
    }

    public List<Expense> recent() {
        return expenseRepo.findTop100ByOrderBySpentOnDescIdDesc();
    }

    @Transactional
    public void delete(Long id) {
        if (!expenseRepo.existsById(id))
            throw new IllegalArgumentException("Expense not found: " + id);
        expenseRepo.deleteById(id);
        auditService.recordAsync("Deleted expense #" + id);
    }
}
