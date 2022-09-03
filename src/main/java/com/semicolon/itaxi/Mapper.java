package com.semicolon.itaxi;

import com.semicolon.itaxi.data.models.Driver;
import com.semicolon.itaxi.dto.response.DriverDto;


public class Mapper {



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
