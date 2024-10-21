package com.expense.budget_app.service.impl;

import com.expense.budget_app.dto.BudgetDTO;
import com.expense.budget_app.entity.Budget;
import com.expense.budget_app.entity.User;
import com.expense.budget_app.repository.BudgetRepository;
import com.expense.budget_app.repository.UserRepository;
import com.expense.budget_app.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    @Override
    public BudgetDTO createOrUpdateBudget(BudgetDTO budgetDTO) {
        User user = getLoggedInUser();

        // Check if a budget already exists for this category
        Optional<Budget> existingBudget = budgetRepository.findByUserIdAndCategory(user.getId(), budgetDTO.getCategory());

        Budget budget = existingBudget.orElse(new Budget());
        budget.setUser(user);
        budget.setCategory(budgetDTO.getCategory());
        budget.setMonthlyLimit(budgetDTO.getMonthlyLimit());

        Budget savedBudget = budgetRepository.save(budget);
        return mapToDTO(savedBudget);
    }

    @Override
    public List<BudgetDTO> getBudgetsByUser(Long userId) {
        return budgetRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BudgetDTO getBudgetByCategory(Long userId, String category) {
        Budget budget = budgetRepository.findByUserIdAndCategory(userId, category)
                .orElseThrow(() -> new RuntimeException("Budget not found for category: " + category));
        return mapToDTO(budget);
    }

    @Override
    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }

    private BudgetDTO mapToDTO(Budget budget) {
        return new BudgetDTO(
                budget.getId(),
                budget.getCategory(),
                budget.getMonthlyLimit(),
                budget.getUser().getId()
        );
    }

    private User getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
