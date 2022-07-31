package com.semicolon.itaxi.data.repositories;

import com.semicolon.itaxi.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
