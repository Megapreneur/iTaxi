package com.semicolon.itaxi;

import com.semicolon.itaxi.data.models.Driver;
import com.semicolon.itaxi.data.models.User;
import com.semicolon.itaxi.dto.requests.BookTripRequest;
import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.response.DriverDto;
import com.semicolon.itaxi.dto.response.RegisterUserResponse;

import java.util.Optional;

public class Mapper {

    public static void map(RegisterUserRequest request, User user) {
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setPassword(user.getPassword());
    }

    public static void map(User savedUser, RegisterUserResponse response) {
        response.setMessage("Welcome " + savedUser.getName() + " to iTaxi. We wish you safe, peaceful and fast trips");
    }

    public static void map(BookTripRequest request, User savedUser) {
        savedUser.setPickUpAddress(request.getPickUpAddress());
        savedUser.setDropOffAddress(request.getDropOffAddress());
    }

    public static void map(Driver savedDriver, DriverDto driverDto) {
        driverDto.setName(savedDriver.getName());
        driverDto.setPhoneNumber(savedDriver.getPhoneNumber());
        driverDto.setModel(savedDriver.getCarType());
        driverDto.setVehicleNumber(savedDriver.getCarNumber());
        driverDto.setColor(savedDriver.getCarColour());
        driverDto.setMessage(savedDriver.getName() + " your registration was successful ");
    }

    public static void map(Optional<Driver> driver, DriverDto driverDto) {
        driverDto.setName(driver.get().getName());
        driverDto.setPhoneNumber(driver.get().getPhoneNumber());
        driverDto.setModel(driver.get().getCarType());
        driverDto.setVehicleNumber(driver.get().getCarNumber());
        driverDto.setColor(driver.get().getCarColour());


//        DriverDto.builder()
//                .color(driver.get().getCarColour())
//                .model(driver.get().getCarType())
//                .vehicleNumber(driver.get().getCarNumber())
//                .name(driver.get().getName())
//                .phoneNumber(driver.get().getPhoneNumber())
//                .build()
    }
}
