package com.expensetracker.repository;

import com.expensetracker.model.Budget;
import com.expensetracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByCategory(Category category);
}
