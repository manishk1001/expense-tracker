package com.expense_tracker.expense_tracker.repository;

import com.expense_tracker.expense_tracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);
    List<Expense> findByUserIdAndCategory(Long userId, String category);

    @Query("SELECT e FROM Expense e WHERE e.userId = :userId AND YEAR(e.createdAt) = :year AND MONTH(e.createdAt) = :month")
    List<Expense> findByUserIdAndYearMonth(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);
}
