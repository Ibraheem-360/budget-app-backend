package com.expense.budget_app.service;

import com.expense.budget_app.dto.ExpenseDTO;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    ExpenseDTO addExpense(ExpenseDTO expenseDTO);
    ExpenseDTO updateExpense(Long id, ExpenseDTO expenseDto);
    List<ExpenseDTO> getAllExpensesByUser(Long userId);
    List<ExpenseDTO> getExpensesByCategory(Long userId, String category);
    List<ExpenseDTO> getExpensesByDateRange(Long userId, LocalDate startDate, LocalDate endDate);
    void deleteExpense(Long id);
}
