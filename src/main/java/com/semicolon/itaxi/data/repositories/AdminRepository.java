package com.semicolon.itaxi.data.repositories;

import com.semicolon.itaxi.data.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
