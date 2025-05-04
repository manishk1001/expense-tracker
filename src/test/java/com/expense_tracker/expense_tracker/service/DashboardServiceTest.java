package com.expense_tracker.expense_tracker.service;

import com.expense_tracker.expense_tracker.dto.DashboardStatsDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class DashboardServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private DashboardService dashboardService;

    private List<Expense> testExpenses;

    @BeforeEach
    void setUp() {
        testExpenses = List.of(
            Expense.builder()
                .amount(BigDecimal.valueOf(100.00))
                .category("Food")
                .date(LocalDate.now())
                .build(),
            Expense.builder()
                .amount(BigDecimal.valueOf(150.00))
                .category("Transport")
                .date(LocalDate.now())
                .build()
        );
    }

    @Test
    void testGetDashboardStats() {
        // Mock repository methods
        when(expenseRepository.findAll()).thenReturn(testExpenses);
        when(expenseRepository.getTotalExpensesByCategory()).thenReturn(List.of(
            new Object[] {"Food", BigDecimal.valueOf(100.00)},
            new Object[] {"Transport", BigDecimal.valueOf(150.00)}
        ));

        // Execute
        DashboardStatsDto stats = dashboardService.getDashboardStats();

        // Verify
        assertNotNull(stats);
        assertEquals(2, stats.getTotalExpenses());
        assertEquals(BigDecimal.valueOf(250.00), stats.getTotalAmount());
        assertEquals(2, stats.getCategoryDistribution().size());
        verify(expenseRepository, times(1)).findAll();
        verify(expenseRepository, times(1)).getTotalExpensesByCategory();
    }

    @Test
    void testGetMonthlyExpenses() {
        // Mock repository method
        when(expenseRepository.getMonthlyExpenses()).thenReturn(testExpenses);

        // Execute
        List<Expense> monthlyExpenses = dashboardService.getMonthlyExpenses();

        // Verify
        assertNotNull(monthlyExpenses);
        assertFalse(monthlyExpenses.isEmpty());
        verify(expenseRepository, times(1)).getMonthlyExpenses();
    }

    @Test
    void testGetCategoryDistribution() {
        // Mock repository method
        when(expenseRepository.getTotalExpensesByCategory()).thenReturn(List.of(
            new Object[] {"Food", BigDecimal.valueOf(100.00)},
            new Object[] {"Transport", BigDecimal.valueOf(150.00)}
        ));

        // Execute
        List<Object[]> distribution = dashboardService.getCategoryDistribution();

        // Verify
        assertNotNull(distribution);
        assertFalse(distribution.isEmpty());
        verify(expenseRepository, times(1)).getTotalExpensesByCategory();
    }
}
