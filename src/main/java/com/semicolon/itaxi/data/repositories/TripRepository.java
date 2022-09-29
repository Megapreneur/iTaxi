package com.semicolon.itaxi.data.repositories;

import com.semicolon.itaxi.data.models.Driver;
import com.semicolon.itaxi.data.models.Trip;
import com.semicolon.itaxi.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findTripsByDriver(Driver driver);

    List<Trip> findTripsByUser(User user);
}
