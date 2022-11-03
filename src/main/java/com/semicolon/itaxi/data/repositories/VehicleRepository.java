package com.semicolon.itaxi.data.repositories;

import com.semicolon.itaxi.data.models.Driver;
import com.semicolon.itaxi.data.models.Vehicle;
import com.semicolon.itaxi.dto.response.DriverDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByDriverId(long id);

    Optional<Vehicle> findVehicleByDriver(DriverDto driver);

    Vehicle getVehicleBtDriver(Driver assignedDriver);
}
