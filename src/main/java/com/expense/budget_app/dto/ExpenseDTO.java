package com.expense.budget_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private Long id;
    private Double amount;
    private String category;
    private LocalDate date;
    private String notes;
    private Long userId;
}
