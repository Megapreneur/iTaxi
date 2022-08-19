package com.semicolon.itaxi.services;

import com.semicolon.itaxi.Mapper;
import com.semicolon.itaxi.data.models.Driver;
import com.semicolon.itaxi.data.repositories.DriverRepository;
import com.semicolon.itaxi.dto.requests.RegisterDriverRequest;
import com.semicolon.itaxi.dto.response.DriverDto;
import com.semicolon.itaxi.exceptions.NoDriverFoundException;
import com.semicolon.itaxi.exceptions.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class DriverServiceImpl implements DriverService{
    @Autowired

    private DriverRepository driverRepository;

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
                .location(request.getLocation())
                .build();
        Driver savedDriver = driverRepository.save(driver);
        DriverDto driverDto = new DriverDto();
        Mapper.map(savedDriver, driverDto);
        return driverDto;
    }

    @Override
    public DriverDto getDriver(String location) throws NoDriverFoundException {
        Optional<Driver> driver = Optional.ofNullable(driverRepository.findByLocation(location));
        if (driver.isPresent()) {
            DriverDto driverDto = new DriverDto();
            Mapper.map(driver, driverDto);
            return driverDto;
        }
        throw new NoDriverFoundException("No driver available at your location");
    }

//    @Override
//    public String toString() {
//        return "Driver{" +
//                "name='" + driverRepository.getName() + '\'' +
//                ", phoneNumber='" + driverRepository.getPhoneNumber() + '\'' +
//                ", carNumber='" + driverRepository.getCarNumber() + '\'' +
//                ", carType='" + driverRepository.getCarType() + '\'' +
//                ", gender=" + driverRepository.getGender() +
//                ", location='" + driverRepository.getLocation() + '\'' +
//                '}';
//    }

}