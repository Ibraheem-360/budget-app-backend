package com.expense.budget_app.controller;

import com.expense.budget_app.dto.BudgetDTO;
import com.expense.budget_app.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ResponseEntity<BudgetDTO> createOrUpdateBudget(@RequestBody BudgetDTO budgetDTO) {
        BudgetDTO savedBudget = budgetService.createOrUpdateBudget(budgetDTO);
        return ResponseEntity.ok(savedBudget);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<BudgetDTO>> getBudgetsByUser(@PathVariable Long userId) {
        List<BudgetDTO> budgets = budgetService.getBudgetsByUser(userId);
        return ResponseEntity.ok(budgets);
    }

    @GetMapping("/{userId}/category")
    public ResponseEntity<BudgetDTO> getBudgetByCategory(
            @PathVariable Long userId,
            @RequestParam String category) {
        BudgetDTO budget = budgetService.getBudgetByCategory(userId, category);
        return ResponseEntity.ok(budget);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}
