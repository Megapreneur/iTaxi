package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.models.Driver;
import com.semicolon.itaxi.data.repositories.DriverRepository;
import com.semicolon.itaxi.dto.requests.RegisterDriverRequest;
import com.semicolon.itaxi.dto.response.DriverDto;
import com.semicolon.itaxi.exceptions.UserExistException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class DriverServiceImpl implements DriverService{
    @Autowired

    private DriverRepository driverRepository;
    private ModelMapper modelMapper;

    @Override
    public DriverDto register(RegisterDriverRequest request) {
        if (driverRepository.existsByEmail(request.getEmail()))throw  new UserExistException("Email Already Exist");
        Driver driver = Driver.builder()
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .carType(request.getCarType())
                .carNumber(request.getCarNumber())
                .carColour(request.getCarColour())
                .gender(request.getGender())
                .build();
        Driver savedDriver = driverRepository.save(driver);
        return modelMapper.map(savedDriver, DriverDto.class);
    }
}
