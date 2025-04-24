package com.expense_tracker.expense_tracker.repository;

import com.expense_tracker.expense_tracker.entity.Expense;
import com.expense_tracker.expense_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
