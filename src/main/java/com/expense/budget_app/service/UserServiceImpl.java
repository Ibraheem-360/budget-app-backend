package com.expense.budget_app.service;

import com.expense.budget_app.dto.RegisterRequest;
import com.expense.budget_app.entity.Role;
import com.expense.budget_app.entity.User;
import com.expense.budget_app.exception.UserAlreadyExistsException;
import com.expense.budget_app.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByUsernameOrEmail(
                registerRequest.getUsername(), registerRequest.getEmail())) {
            throw new UserAlreadyExistsException("Username or email is already taken.");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.ROLE_USER);  // Default role as USER

        return userRepository.save(user);
    }

    public User registerFirstAdmin(RegisterRequest registerRequest) {
        if (userRepository.existsByIsFirstAdminTrue()) {
            throw new UserAlreadyExistsException("First admin already exists.");
        }

        User admin = new User();
        admin.setUsername(registerRequest.getUsername());
        admin.setEmail(registerRequest.getEmail());
        admin.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        admin.setRole(Role.ROLE_ADMIN);
        admin.setFirstAdmin(true);

        return userRepository.save(admin);
    }

    public User registerAdmin(RegisterRequest registerRequest) {
        if (userRepository.existsByUsernameOrEmail(
                registerRequest.getUsername(), registerRequest.getEmail())) {
            throw new UserAlreadyExistsException("Username or email is already taken.");
        }

        User admin = new User();
        admin.setUsername(registerRequest.getUsername());
        admin.setEmail(registerRequest.getEmail());
        admin.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        admin.setRole(Role.ROLE_ADMIN);

        return userRepository.save(admin);
    }
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean isFirstAdminRegistered() {
        return userRepository.existsByIsFirstAdminTrue();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();  // Fetch all users from the database
    }
}
