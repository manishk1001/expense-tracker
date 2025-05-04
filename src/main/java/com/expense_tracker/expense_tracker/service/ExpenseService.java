package com.expense_tracker.expense_tracker.service;

import com.expense_tracker.expense_tracker.dto.ExpenseDTO;
import com.expense_tracker.expense_tracker.enums.Category;
import com.expense_tracker.expense_tracker.entity.Expense;
import com.expense_tracker.expense_tracker.entity.User;
import com.expense_tracker.expense_tracker.exception.ResourceNotFoundException;
import com.expense_tracker.expense_tracker.exception.ResponseCodeEnum;
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

    public ExpenseDTO createExpense(long userId, ExpenseDTO expenseDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Expense expense = Expense.builder()
                .user(user)
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
        return expenseRepository.findByUser_UserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ExpenseDTO> getExpensesByCategory(long userId, Category category) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return expenseRepository.findByUser_UserIdAndCategory(user.getUserId(), category.name())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ExpenseDTO> getExpensesByMonth(long userId, String yearMonth) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseCodeEnum.USER_NOT_FOUND));

        try {
            LocalDate date = LocalDate.parse(yearMonth + "-01", DateTimeFormatter.ISO_DATE);
            int year = date.getYear();
            int month = date.getMonthValue();

            return expenseRepository.findByUser_UserIdAndYearMonth(user.getUserId(), year, month)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (DateTimeParseException e) {
            throw new ResourceNotFoundException(ResponseCodeEnum.INVALID_TIME_RANGE);
        }
    }

    public ExpenseDTO updateExpense(ExpenseDTO expenseDTO) {
        Expense existingExpense = expenseRepository.findById(expenseDTO.getExpenseId())
                .orElseThrow(() -> new ResourceNotFoundException(ResponseCodeEnum.EXPENSE_NOT_FOUND));

        existingExpense.setDescription(expenseDTO.getDescription());
        existingExpense.setCategory(expenseDTO.getCategory());
        existingExpense.setAmount(expenseDTO.getAmount());

        Expense updatedExpense = expenseRepository.save(existingExpense);
        return convertToDTO(updatedExpense);
    }

    public void deleteExpense(Long expenseId) {
        if (!expenseRepository.existsById(expenseId)) {
            throw new ResourceNotFoundException(ResponseCodeEnum.EXPENSE_NOT_FOUND);
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
