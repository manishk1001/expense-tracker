package com.expense_tracker.expense_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PieChart {
    private Double need;
    private Double want;
    private Double savings;

}
