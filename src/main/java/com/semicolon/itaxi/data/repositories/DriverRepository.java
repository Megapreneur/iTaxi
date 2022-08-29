package com.semicolon.itaxi.data.repositories;

import com.semicolon.itaxi.data.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface DriverRepository extends JpaRepository<Driver, Integer> {
    boolean existsByEmail(String email);


    List<Driver> findByLocation(String location);

    Optional<Driver> findByEmail(String email);


//    String getName();
//
//    String getPhoneNumber();
//
//    String getCarNumber();
//
//    String getCarType();
//
//    String getGender();
//
//    String getLocation();
}
