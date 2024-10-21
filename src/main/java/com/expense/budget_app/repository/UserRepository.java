package com.expense.budget_app.repository;

import com.expense.budget_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    boolean existsByUsernameOrEmail(String username, String email);

    boolean existsByIsFirstAdminTrue();

    Optional<User> findByEmail(String email);
}
