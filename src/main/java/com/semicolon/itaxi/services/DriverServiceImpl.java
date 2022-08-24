package com.semicolon.itaxi.services;

import com.semicolon.itaxi.Mapper;
import com.semicolon.itaxi.data.models.Driver;
import com.semicolon.itaxi.data.repositories.DriverRepository;
import com.semicolon.itaxi.dto.requests.LoginDriverRequest;
import com.semicolon.itaxi.dto.requests.RegisterDriverRequest;
import com.semicolon.itaxi.dto.response.DriverDto;
import com.semicolon.itaxi.exceptions.InvalidDriverException;
import com.semicolon.itaxi.exceptions.NoDriverFoundException;
import com.semicolon.itaxi.exceptions.UserExistException;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
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
        Optional<Driver> driver = driverRepository.findByLocation(location);
        if (driver.isPresent()) {
            return DriverDto.builder()
                    .name(driver.get().getName())
                    .model(driver.get().getCarType())
                    .color(driver.get().getCarColour())
                    .phoneNumber(driver.get().getPhoneNumber())
                    .vehicleNumber(driver.get().getCarNumber())
                    .build();
        }
        throw new NoDriverFoundException("No driver available at your location");
    }

    @Override
    @Transactional
    public DriverDto login(LoginDriverRequest request) throws InvalidDriverException {
        Optional<Driver> driver = driverRepository.findByEmail(request.getEmail());

//        if(driver.get().getLocation() != null && !Objects.equals(driver, request.getLocation())){
//            driver.get().setLocation(request.getLocation());
//        }
        if (driver.isPresent()){
            driver.get().setName(driver.get().getName());
            driver.get().setAddress(driver.get().getAddress());
//            driver.get().setEmail(driver.get().getEmail());
            driver.get().setPhoneNumber(driver.get().getPhoneNumber());
            driver.get().setLocation(request.getLocation());
            driver.get().setCarColour(driver.get().getCarColour());
            driver.get().setCarNumber(driver.get().getCarNumber());
            driver.get().setCarType(driver.get().getCarType());
            driver.get().setGender(driver.get().getGender());

            Driver savedDriver = driverRepository.save(driver.get());
            DriverDto driverDto = new DriverDto();
            driverDto.setMessage("Looking for nearby orders at " + savedDriver.getLocation());
            return driverDto;
        }
        throw new InvalidDriverException("Invalid Driver details");
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