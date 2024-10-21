package com.expense.budget_app.service;

import com.expense.budget_app.dto.ExpenseDTO;
import com.expense.budget_app.entity.Expense;
import com.expense.budget_app.entity.User;
import com.expense.budget_app.repository.ExpenseRepository;
import com.expense.budget_app.service.ExpenseService;
import com.expense.budget_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    @Override
    public ExpenseDTO addExpense(ExpenseDTO expenseDTO) {
        User currentUser = getLoggedInUser();  // Get the current logged-in user

        Expense expense = new Expense();
        expense.setUser(currentUser);
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());
        expense.setNotes(expenseDTO.getNotes());
        // Date is automatically set by the entity

        Expense savedExpense = expenseRepository.save(expense);
        return mapToDTO(savedExpense);
    }

    @Override
    public List<ExpenseDTO> getAllExpensesByUser(Long userId) {
        return expenseRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpenseDTO> getExpensesByCategory(Long userId, String category) {
        return expenseRepository.findByUserIdAndCategory(userId, category)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpenseDTO> getExpensesByDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public ExpenseDTO updateExpense(Long id, ExpenseDTO expenseDTO) {
        // Find the existing expense by ID
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));

        // Update only the fields provided (leave others unchanged)
        if (expenseDTO.getAmount() != null) {
            existingExpense.setAmount(expenseDTO.getAmount());
        }
        if (expenseDTO.getCategory() != null) {
            existingExpense.setCategory(expenseDTO.getCategory());
        }
        if (expenseDTO.getNotes() != null) {
            existingExpense.setNotes(expenseDTO.getNotes());
        }
        if (expenseDTO.getDate() != null) {
            existingExpense.setDate(expenseDTO.getDate());
        }

        // Save the updated expense
        Expense savedExpense = expenseRepository.save(existingExpense);

        return mapToDTO(savedExpense);
    }


    private ExpenseDTO mapToDTO(Expense expense) {
        return new ExpenseDTO(
                expense.getId(),
                expense.getAmount(),
                expense.getCategory(),
                expense.getDate(),
                expense.getNotes(),
                expense.getUser().getId()
        );
    }

    private User getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
