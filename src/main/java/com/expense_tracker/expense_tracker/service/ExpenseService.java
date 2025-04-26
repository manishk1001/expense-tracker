package com.expense_tracker.expense_tracker.service;

import com.expense_tracker.expense_tracker.dto.ExpenseDTO;
import com.expense_tracker.expense_tracker.entity.Category;
import com.expense_tracker.expense_tracker.entity.Expense;
import com.expense_tracker.expense_tracker.entity.User;
import com.expense_tracker.expense_tracker.repository.ExpenseRepository;
import com.expense_tracker.expense_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;

    public ExpenseDTO createExpense(String emailId, ExpenseDTO expenseDTO) {
        User user = userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Expense expense = Expense.builder()
                .userId(user.getUserId())
                .description(expenseDTO.getDescription())
                .category(expenseDTO.getCategory())
                .amount(expenseDTO.getAmount())
                .build();

        Expense savedExpense = expenseRepository.save(expense);
        return convertToDTO(savedExpense);
    }

    public ExpenseDTO getExpense(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        return convertToDTO(expense);
    }

    public List<ExpenseDTO> getAllExpensesByUserId(Long userId) {
        return expenseRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ExpenseDTO> getExpensesByCategory(String emailId, Category category) {
        User user = userRepository.findByEmailId(emailId).orElseThrow(() -> new RuntimeException("User not found"));
        return expenseRepository.findByUserIdAndCategory(user.getUserId(), category.name())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ExpenseDTO> getExpensesByMonth(String emailId, String yearMonth) {
        User user = userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            LocalDate date = LocalDate.parse(yearMonth + "-01", DateTimeFormatter.ISO_DATE);
            int year = date.getYear();
            int month = date.getMonthValue();

            return expenseRepository.findByUserIdAndYearMonth(user.getUserId(), year, month)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: YYYY-MM");
        }
    }

    public ExpenseDTO updateExpense(ExpenseDTO expenseDTO) {
        Expense existingExpense = expenseRepository.findById(expenseDTO.getExpenseId())
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        existingExpense.setDescription(expenseDTO.getDescription());
        existingExpense.setCategory(expenseDTO.getCategory());
        existingExpense.setAmount(expenseDTO.getAmount());

        Expense updatedExpense = expenseRepository.save(existingExpense);
        return convertToDTO(updatedExpense);
    }

    public void deleteExpense(Long expenseId) {
        if (!expenseRepository.existsById(expenseId)) {
            throw new RuntimeException("Expense not found");
        }
        expenseRepository.deleteById(expenseId);
    }

    private ExpenseDTO convertToDTO(Expense expense) {
        return ExpenseDTO.builder()
                .expenseId(expense.getExpenseId())
                .description(expense.getDescription())
                .category(expense.getCategory())
                .amount(expense.getAmount())
                .createdAt(expense.getCreatedAt())
                .build();
    }
}
