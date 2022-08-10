package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.models.Driver;
import com.semicolon.itaxi.data.repositories.DriverRepository;
import com.semicolon.itaxi.dto.requests.RegisterDriverRequest;
import com.semicolon.itaxi.dto.response.RegisterDriverResponse;
import com.semicolon.itaxi.exceptions.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class DriverServiceImpl implements DriverService{
    @Autowired

    private DriverRepository driverRepository;

    @Override
    public RegisterDriverResponse register(RegisterDriverRequest request) {
        if (driverRepository.existsByEmail(request.getEmail()))throw  new UserExistException("Email Already Exist");
        Driver driver = Driver.builder()
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .carType(request.getCarType())
                .carNumber(request.getCarNumber())
                .carColour(request.getCarColour())
                .build();
        Driver savedDriver = driverRepository.save(driver);

        return null;
    }
}
