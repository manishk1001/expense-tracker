package com.expense_tracker.expense_tracker.controller;

import com.expense_tracker.expense_tracker.dto.ExpenseDTO;
import com.expense_tracker.expense_tracker.entity.Category;
import com.expense_tracker.expense_tracker.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDTO, Authentication authentication) {
        String emailId = authentication.getName();
        return ResponseEntity.ok(expenseService.createExpense(emailId, expenseDTO));
    }

    @GetMapping("/{yearMonth}")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByMonth(
            @PathVariable String yearMonth,
            Authentication authentication) {
        String emailId = authentication.getName();
        return ResponseEntity.ok(expenseService.getExpensesByMonth(emailId, yearMonth));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByCategory(
            @PathVariable Category category,
            Authentication authentication) {
        String emailId = authentication.getName();
        return ResponseEntity.ok(expenseService.getExpensesByCategory(emailId, category));
    }

    @PutMapping
    public ResponseEntity<ExpenseDTO> updateExpense(@RequestBody ExpenseDTO expenseDTO) {
        return ResponseEntity.ok(expenseService.updateExpense(expenseDTO));
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.ok().build();
    }
}