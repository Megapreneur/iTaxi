package com.semicolon.itaxi.data.repositories;

import com.semicolon.itaxi.data.models.Payment;
import com.semicolon.itaxi.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
