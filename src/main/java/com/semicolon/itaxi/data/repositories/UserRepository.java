package com.semicolon.itaxi.data.repositories;

import com.semicolon.itaxi.data.models.User;
import com.semicolon.itaxi.dto.requests.LoginUserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
