package com.semicolon.itaxi.data.repositories;

import com.semicolon.itaxi.data.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findByUserId(long id);

    List<Trip> findByDriverId(long id);
}
