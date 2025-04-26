package com.expense_tracker.expense_tracker.controller;

import com.expense_tracker.expense_tracker.dto.ExpenseDTO;
import com.expense_tracker.expense_tracker.dto.PieChart;
import com.expense_tracker.expense_tracker.entity.Category;
import com.expense_tracker.expense_tracker.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;
    @GetMapping("/pie-chart/{yearMonth}")
    public ResponseEntity<PieChart> getPieChart(@PathVariable String yearMonth, Authentication authentication) {
        String emailId = authentication.getName();
        return ResponseEntity.ok(dashboardService.getPieChart(emailId, yearMonth));
    }
    @GetMapping("/table/{category}/{yearMonth}")
    public ResponseEntity<List<ExpenseDTO>> getTableByCategory(@PathVariable String category, @PathVariable String yearMonth,
            Authentication authentication) {
        String emailId = authentication.getName();
        return ResponseEntity.ok(dashboardService.getTableByCategory(emailId, category, yearMonth));
    }
}
