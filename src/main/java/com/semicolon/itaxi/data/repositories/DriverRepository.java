package com.semicolon.itaxi.data.repositories;

import com.semicolon.itaxi.data.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DriverRepository extends JpaRepository<Driver, Integer> {

}
