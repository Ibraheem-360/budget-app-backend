package com.expense.budget_app.service;

import com.expense.budget_app.dto.BudgetDTO;

import java.util.List;

public interface BudgetService {
    BudgetDTO createOrUpdateBudget(BudgetDTO budgetDTO);
    List<BudgetDTO> getBudgetsByUser(Long userId);
    BudgetDTO getBudgetByCategory(Long userId, String category);
    void deleteBudget(Long id);
}
