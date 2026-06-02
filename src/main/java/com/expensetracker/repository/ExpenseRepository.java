package com.expensetracker.repository;

import com.expensetracker.dto.CategoryTotal;
import com.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findTop100ByOrderBySpentOnDescIdDesc();

    long countBySpentOnBetween(LocalDate start, LocalDate end);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.spentOn BETWEEN :start AND :end")
    BigDecimal sumBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e")
    BigDecimal sumAll();

    // GROUP BY aggregation mapped to a projection (category -> total spent)
    @Query("SELECT e.category AS category, SUM(e.amount) AS total " +
           "FROM Expense e WHERE e.spentOn BETWEEN :start AND :end GROUP BY e.category")
    List<CategoryTotal> totalsByCategoryBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
