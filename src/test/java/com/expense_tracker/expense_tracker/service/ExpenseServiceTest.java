package com.expense_tracker.expense_tracker.service;

import com.expense_tracker.expense_tracker.dto.ExpenseDto;
import com.expense_tracker.expense_tracker.entity.Expense;
import com.expense_tracker.expense_tracker.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    private Expense testExpense;
    private ExpenseDto testExpenseDto;

    @BeforeEach
    void setUp() {
        testExpense = Expense.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100.00))
                .description("Test expense")
                .date(LocalDate.now())
                .category("Food")
                .build();

        testExpenseDto = ExpenseDto.builder()
                .amount(BigDecimal.valueOf(100.00))
                .description("Test expense")
                .date(LocalDate.now())
                .category("Food")
                .build();
    }

    @Test
    void testCreateExpense_Success() {
        // Mock repository save
        when(expenseRepository.save(any(Expense.class))).thenReturn(testExpense);

        // Execute
        ExpenseDto result = expenseService.createExpense(testExpenseDto);

        // Verify
        assertNotNull(result);
        assertEquals(testExpense.getAmount(), result.getAmount());
        assertEquals(testExpense.getDescription(), result.getDescription());
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void testGetExpenseById_Success() {
        // Mock repository find
        when(expenseRepository.findById(any(Long.class))).thenReturn(Optional.of(testExpense));

        // Execute
        ExpenseDto result = expenseService.getExpenseById(1L);

        // Verify
        assertNotNull(result);
        assertEquals(testExpense.getAmount(), result.getAmount());
        verify(expenseRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void testGetExpenseById_NotFound() {
        // Mock repository find
        when(expenseRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // Execute and expect exception
        assertThrows(IllegalArgumentException.class, () -> {
            expenseService.getExpenseById(1L);
        });

        verify(expenseRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void testGetAllExpenses() {
        // Mock repository find all
        when(expenseRepository.findAll()).thenReturn(List.of(testExpense));

        // Execute
        List<ExpenseDto> result = expenseService.getAllExpenses();

        // Verify
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(expenseRepository, times(1)).findAll();
    }

    @Test
    void testDeleteExpense_Success() {
        // Mock repository delete
        doNothing().when(expenseRepository).deleteById(any(Long.class));

        // Execute
        expenseService.deleteExpense(1L);

        // Verify
        verify(expenseRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    void testUpdateExpense_Success() {
        // Mock repository find and save
        when(expenseRepository.findById(any(Long.class))).thenReturn(Optional.of(testExpense));
        when(expenseRepository.save(any(Expense.class))).thenReturn(testExpense);

        // Execute
        ExpenseDto result = expenseService.updateExpense(1L, testExpenseDto);

        // Verify
        assertNotNull(result);
        assertEquals(testExpense.getAmount(), result.getAmount());
        verify(expenseRepository, times(1)).findById(any(Long.class));
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void testUpdateExpense_NotFound() {
        // Mock repository find
        when(expenseRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // Execute and expect exception
        assertThrows(IllegalArgumentException.class, () -> {
            expenseService.updateExpense(1L, testExpenseDto);
        });

        verify(expenseRepository, times(1)).findById(any(Long.class));
        verify(expenseRepository, never()).save(any(Expense.class));
    }
}
