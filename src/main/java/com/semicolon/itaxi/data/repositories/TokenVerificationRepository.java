package com.semicolon.itaxi.data.repositories;

import com.semicolon.itaxi.data.models.TokenVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenVerificationRepository extends JpaRepository<TokenVerification, Long> {
}
