package com.semicolon.itaxi;

import com.semicolon.itaxi.data.models.Driver;
import com.semicolon.itaxi.data.models.User;
import com.semicolon.itaxi.dto.requests.BookTripRequest;
import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.response.DriverDto;
import com.semicolon.itaxi.dto.response.RegisterUserResponse;

import java.util.Optional;

public class Mapper {

//    public static void map(RegisterUserRequest request, User user) {
//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
//        user.setPhoneNumber(request.getPhoneNumber());
//        user.setAddress(request.getAddress());
//        user.setPassword(user.getPassword());
//    }
//
//    public static void map(User savedUser, RegisterUserResponse response) {
//        response.setMessage("Welcome " + savedUser.getName() + " to iTaxi. We wish you safe, peaceful and fast trips");
//    }
//
//    public static void map(BookTripRequest request, User savedUser) {
//        savedUser.setPickUpAddress(request.getPickUpAddress());
//        savedUser.setDropOffAddress(request.getDropOffAddress());
//    }

    public static void map(Driver savedDriver, DriverDto driverDto) {
        driverDto.setName(savedDriver.getName());
        driverDto.setPhoneNumber(savedDriver.getPhoneNumber());
        driverDto.setModel(savedDriver.getCarType());
        driverDto.setVehicleNumber(savedDriver.getCarNumber());
        driverDto.setColor(savedDriver.getCarColour());
        driverDto.setMessage(savedDriver.getName() + " your registration was successful ");
    }



    public static void mapper(Driver assignDriver, DriverDto driverDto) {
        driverDto.setName(assignDriver.getName());
        driverDto.setModel(assignDriver.getCarType());
        driverDto.setColor(assignDriver.getCarColour());
        driverDto.setPhoneNumber(assignDriver.getPhoneNumber());
        driverDto.setVehicleNumber(assignDriver.getCarNumber());
    }
}
