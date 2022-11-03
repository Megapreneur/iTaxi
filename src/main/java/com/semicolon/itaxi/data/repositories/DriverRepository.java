package com.semicolon.itaxi.data.repositories;

import com.semicolon.itaxi.data.models.Driver;
import com.semicolon.itaxi.dto.response.DriverDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface DriverRepository extends JpaRepository<Driver, Long> {
    boolean existsByEmail(String email);

    List<Driver> findByLocation(String location);

//    List<Driver> findByDriverStatus(DriverStatus driverStatus);

    Optional<Driver> findByEmail(String email);

//    @Query(value = "SELECT new com.semicolon.itaxi.dto.response.DriverDto (driver.name, driver.phoneNumber, car.carColour, car.carModel, car.carNumber)from Drivers driver JOIN cars car")
//    DriverDto getDriverDetails(Driver driver);

}
