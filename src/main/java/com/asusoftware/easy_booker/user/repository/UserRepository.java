package com.asusoftware.easy_booker.user.repository;

import com.asusoftware.easy_booker.user.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email); // Metodă nouă pentru căutare după email
}