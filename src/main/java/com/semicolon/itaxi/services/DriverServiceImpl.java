package com.semicolon.itaxi.services;


import com.semicolon.itaxi.data.models.Driver;
import com.semicolon.itaxi.data.models.Vehicle;
import com.semicolon.itaxi.data.models.enums.DriverStatus;
import com.semicolon.itaxi.data.repositories.DriverRepository;
import com.semicolon.itaxi.data.repositories.VehicleRepository;
import com.semicolon.itaxi.dto.requests.LoginDriverRequest;
import com.semicolon.itaxi.dto.requests.PaymentRequest;
import com.semicolon.itaxi.dto.requests.RegisterDriverRequest;
import com.semicolon.itaxi.dto.requests.RegisterVehicleRequest;
import com.semicolon.itaxi.dto.response.*;
import com.semicolon.itaxi.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service

public class DriverServiceImpl implements DriverService{

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private VehicleRepository vehicleRepository;


    @Override
    public RegisterDriverResponse register(RegisterDriverRequest request) throws MismatchedPasswordException, UserExistException {
        if (driverRepository.existsByEmail(request.getEmail())) throw  new UserExistException("User Already Exist", HttpStatus.FORBIDDEN);
            Driver driver = Driver
                    .builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .gender(request.getGender())
                    .address(request.getAddress())
                    .password(request.getPassword())
                    .confirmPassword(request.getConfirmPassword())
                    .build();
            if(request.getPassword().equals(request.getConfirmPassword())) {
                Driver savedDrive = driverRepository.save(driver);
                return RegisterDriverResponse
                        .builder()
                        .message("Hello " + savedDrive.getName() + " , Your registration was successful")
                        .build();
            }
        throw new MismatchedPasswordException("Password does not match!!!", HttpStatus.FORBIDDEN);
    }

    @Override
    public DriverDto getDriver(String location) throws NoDriverFoundException {
        List<Driver> drivers = driverRepository.findByLocation(location);
        List<Driver> availableDriver;
        for (int i = 0; i < drivers.size(); i++) {
            if(drivers.get(i).getDriverStatus().equals(DriverStatus.AVAILABLE)){
                availableDriver = drivers.add(drivers.get(i));
                if (!availableDriver.isEmpty()){
                    SecureRandom random = new SecureRandom();
                    Driver assignDriver = drivers.get(random.nextInt(drivers.size()));
                    Optional<Vehicle> savedVehicle1 = vehicleRepository.findByDriverId(assignDriver.getId());
                    if (savedVehicle1.isPresent()){
                        return DriverDto
                                .builder()
                                .message("")
                                .name(assignDriver.getName())
                                .phoneNumber(assignDriver.getPhoneNumber())
                                .vehicleNumber(savedVehicle1.get().getCarNumber())
                                .color(savedVehicle1.get().getCarColour())
                                .model(savedVehicle1.get().getCarModel())
                                .build();
                    }
                }
            }
            throw new NoDriverFoundException("No driver available at your location", HttpStatus.NOT_FOUND);
        }
        throw new NoDriverFoundException("No driver available at your location", HttpStatus.NOT_FOUND);
    }

    @Override
    public RegisterDriverResponse registerVehicle(RegisterVehicleRequest request) throws InvalidDriverException, InvalidActionException {
        Optional<Driver> driver = driverRepository.findByEmail(request.getEmail());
        if (driver.isPresent()){
            Optional<Vehicle> savedVehicle = vehicleRepository.findByDriverId(driver.get().getId());
            if (savedVehicle.isEmpty()){
                Vehicle vehicle = Vehicle
                        .builder()
                        .carNumber(request.getCarNumber())
                        .carModel(request.getCarModel())
                        .carColour(request.getCarColour())
                        .driver(driver.get())
                        .build();
                vehicleRepository.save(vehicle);
                return RegisterDriverResponse
                        .builder()
                        .message(driver.get().getName() + " your car has been registered successfully. Safe trips")
                        .build();
            }
            throw new InvalidActionException("You can't register more than a car !!!", HttpStatus.FORBIDDEN);
        }
            throw new InvalidDriverException("Invalid Driver details", HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public LoginDriverResponse login(LoginDriverRequest request) throws InvalidDriverException, IncorrectPasswordException {
        Optional<Driver> driver = driverRepository.findByEmail(request.getEmail());
        if (driver.isPresent()){
            if (driver.get().getPassword().equals(request.getPassword())){
                driver.get().setDriverStatus(request.getDriverStatus());
                driver.get().setLocation(request.getLocation());
                driverRepository.save(driver.get());
                return LoginDriverResponse
                        .builder()
                        .message("Welcome back " + driver.get().getName() + ". Ready to go for some rides ?")
                        .build();
            }
            throw new IncorrectPasswordException("Incorrect Password", HttpStatus.NOT_ACCEPTABLE);
        }
            throw new InvalidDriverException("Invalid Driver details", HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public BookingResponse bookingDetails() {
        return null;
    }

    @Override
    public PaymentResponse payment(PaymentRequest request) {
        return null;
    }


//    @Override
//    public DriverDto getDriver(String location) throws NoDriverFoundException {
//        List<Driver> drivers = driverRepository.findByLocation(location);
//        List<Driver> availableDriver;
//        for (int i = 0; i < drivers.size(); i++) {
//            if (drivers.get(i).getDriverStatus().equals(AVAILABLE)) {
//                availableDriver = drivers.stream().toList();
//                if (!availableDriver.isEmpty()) {
//                    if (availableDriver.size() == 1) {
//                        Driver assignDriver = drivers.get(0);
//                        DriverDto driverDto = new DriverDto();
//                        Mapper.mapper(assignDriver, driverDto);
//                        return driverDto;
//                    } else {
//                        SecureRandom random = new SecureRandom();
//                        Driver assignDriver = drivers.get(random.nextInt(drivers.size()));
//                        DriverDto driverDto = new DriverDto();
//                        Mapper.mapper(assignDriver, driverDto);
//                        return driverDto;
//                    }
//                }
//                throw new NoDriverFoundException("No driver available at your location");
//            }
//        }
//        throw new NoDriverFoundException("No driver available at your location");
//    }
//
//
//    @Override
//    public BookingResponse bookingDetails() {
//        BookTripRequest request = new BookTripRequest();
//
//        return null;
//    }
//
//    @Override
//    public PaymentResponse payment(PaymentRequest request) {
//        Optional<Driver> driver = driverRepository.findByEmail(request.getEmail());
//        if (driver.isPresent()){
//            Payment payment = Payment
//                    .builder()
//                    .paymentType(request.getPaymentType())
//                    .amount(request.getAmount())
//                    .userEmail(request.getUserEmail())
//                    .build();
//            Payment savedPayment = paymentRepository.save(payment);
//
//            PaymentResponse response = new PaymentResponse();
//            response.setMessage(savedPayment);
//
//
//        }
//        throw new InvalidUserException("Invalid Email") ;
//    }
}