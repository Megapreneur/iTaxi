package com.semicolon.itaxi.controllers;

import com.semicolon.itaxi.data.models.Trip;
import com.semicolon.itaxi.dto.requests.LoginDriverRequest;
import com.semicolon.itaxi.dto.requests.RegisterDriverRequest;
import com.semicolon.itaxi.dto.requests.RegisterVehicleRequest;
import com.semicolon.itaxi.dto.requests.TripHistoryRequest;
import com.semicolon.itaxi.dto.response.ApiResponse;
import com.semicolon.itaxi.dto.response.DriverDto;
import com.semicolon.itaxi.dto.response.LoginDriverResponse;
import com.semicolon.itaxi.dto.response.RegisterDriverResponse;
import com.semicolon.itaxi.exceptions.*;
import com.semicolon.itaxi.services.DriverService;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/iTaxi/drivers")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService){
        this.driverService = driverService;
    }
    @PostMapping("/register")
    public ResponseEntity<?>createDriver(@RequestBody @Valid @NotNull RegisterDriverRequest registerDriverRequest) throws MismatchedPasswordException, UserExistException {
        log.info("Account Creation Request ==> {}", registerDriverRequest);
        RegisterDriverResponse driverDto = driverService.register(registerDriverRequest);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status("success")
                .message("Driver created successfully")
                .data(driverDto)
                .build();
        log.info("Returning response");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @PutMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDriverRequest request) throws InvalidDriverException, IncorrectPasswordException {
        LoginDriverResponse driverDto = driverService.login(request);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status("Success")
                .message("Welcome Back " + driverDto)
                .data(driverDto)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping("/registerYourCar")
    public ResponseEntity<?> registerVehicle(@RequestBody RegisterVehicleRequest request) throws InvalidDriverException, InvalidActionException {
        RegisterDriverResponse driverDto = driverService.registerVehicle(request);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status("Okay")
                .message("Successful Registration")
                .data(driverDto)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping("/trips/{email}")
    public List<Trip> getHistoryOfAllTrips(@PathVariable String email) throws NoTripHistoryForUserException{
        return driverService.getHistoryOfAllTrips(email);
    }
}
