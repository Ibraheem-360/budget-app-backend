package com.expense.budget_app.service;


import com.expense.budget_app.dto.RegisterRequest;
import com.expense.budget_app.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(RegisterRequest user);
    Optional<User> findByUsername(String username);

    User registerAdmin(RegisterRequest user);

    User registerFirstAdmin(RegisterRequest user);

    boolean isFirstAdminRegistered();

    List<User> getAllUsers();
}
