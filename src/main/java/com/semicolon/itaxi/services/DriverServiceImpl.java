package com.semicolon.itaxi.services;

import com.semicolon.itaxi.Mapper;
import com.semicolon.itaxi.data.models.Driver;
import com.semicolon.itaxi.data.repositories.DriverRepository;
import com.semicolon.itaxi.dto.requests.LoginDriverRequest;
import com.semicolon.itaxi.dto.requests.PaymentRequest;
import com.semicolon.itaxi.dto.requests.RegisterDriverRequest;
import com.semicolon.itaxi.dto.response.BookingResponse;
import com.semicolon.itaxi.dto.response.DriverDto;
import com.semicolon.itaxi.dto.response.PaymentResponse;
import com.semicolon.itaxi.exceptions.IncorrectPasswordException;
import com.semicolon.itaxi.exceptions.InvalidDriverException;
import com.semicolon.itaxi.exceptions.NoDriverFoundException;
import com.semicolon.itaxi.exceptions.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.List;
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
        if (request.getPassword().equals(request.getConfirmPassword())){
            Driver savedDriver = driverRepository.save(driver);
            DriverDto driverDto = new DriverDto();
            Mapper.map(savedDriver, driverDto);
            return driverDto;
        }else {
            throw new IncorrectPasswordException("Password does not match");
        }
    }

    @Override
    public DriverDto getDriver(String location) throws NoDriverFoundException {
        List<Driver> driver = driverRepository.findByLocation(location);
        if (!driver.isEmpty()) {
            if(driver.size() == 1){
                Driver assignDriver = driver.get(0);
                DriverDto driverDto = new DriverDto();
                Mapper.mapper(assignDriver, driverDto);
                return driverDto;
            }else {
                SecureRandom random = new SecureRandom();
                Driver assignDriver = driver.get(random.nextInt(driver.size()));
                DriverDto driverDto = new DriverDto();
                Mapper.mapper(assignDriver, driverDto);
                return driverDto;
            }
        }
        throw new NoDriverFoundException("No driver available at your location");
    }

    @Override
    @Transactional
    public DriverDto login(LoginDriverRequest request) throws InvalidDriverException {
        Optional<Driver> driver = driverRepository.findByEmail(request.getEmail());
        if (driver.isPresent()){
            if (driver.get().getPassword().equals(request.getPassword())){
                driver.get().setName(driver.get().getName());
                driver.get().setAddress(driver.get().getAddress());
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
            throw new IncorrectPasswordException("Incorrect Password");
        }
        throw new InvalidDriverException("Invalid Driver details");
    }

    @Override
    public BookingResponse bookingDetails() {
        return null;
    }

    @Override
    public PaymentResponse payment(PaymentRequest request) {
        return null;
    }
}