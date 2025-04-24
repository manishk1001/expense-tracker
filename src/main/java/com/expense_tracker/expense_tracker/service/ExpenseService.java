package com.expense_tracker.expense_tracker.service;

import com.expense_tracker.expense_tracker.dto.ExpenseDTO;
import com.expense_tracker.expense_tracker.entity.Expense;
import com.expense_tracker.expense_tracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    public ExpenseDTO createExpense(ExpenseDTO expenseDTO) {
        Expense expense = new Expense();
        expense.setUserId(expenseDTO.getUserId());
        expense.setDescription(expenseDTO.getDescription());
        expense.setAmount(expenseDTO.getAmount());
        expenseRepository.save(expense);
        return expenseDTO;
    }
}
