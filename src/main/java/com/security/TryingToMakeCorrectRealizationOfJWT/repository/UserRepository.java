package com.security.TryingToMakeCorrectRealizationOfJWT.repository;

import com.security.TryingToMakeCorrectRealizationOfJWT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
