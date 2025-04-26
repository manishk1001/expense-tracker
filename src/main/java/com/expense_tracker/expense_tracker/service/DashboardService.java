package com.expense_tracker.expense_tracker.service;

import com.expense_tracker.expense_tracker.dto.ExpenseDTO;
import com.expense_tracker.expense_tracker.dto.PieChart;
import com.expense_tracker.expense_tracker.entity.Category;
import com.expense_tracker.expense_tracker.entity.User;
import com.expense_tracker.expense_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final UserRepository userRepository;
    private final ExpenseService expenseService;

    public List<ExpenseDTO> getTableByCategory(String emailId, String category, String yearMonth) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByMonth(emailId, yearMonth);
        List<ExpenseDTO> top5 = new ArrayList<>();
        for(ExpenseDTO expense : expenses){
            if(expense.getCategory().toString().equals(category)){
                top5.add(expense);
            }
        }
        return top5.stream()
                .sorted(Comparator.comparing(ExpenseDTO::getAmount).reversed())
                .limit(5)
                .collect(Collectors.toList());

    }

    public PieChart getPieChart(String emailId, String yearMonth) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByMonth(emailId, yearMonth);
        double need = 0;
        double want = 0;
        double savings;
        for(ExpenseDTO expense : expenses){
            if(expense.getCategory() == Category.NEED){
                need += expense.getAmount();
            }
            else if(expense.getCategory() == Category.WANT){
                want += expense.getAmount();
            }
        }
        User user = userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        savings = user.getMonthlyIncome() - (need + want);
        return PieChart.builder()
                .need(need)
                .want(want)
                .savings(savings)
                .build();
    }
}
