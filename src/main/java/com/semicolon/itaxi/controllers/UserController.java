package com.semicolon.itaxi.controllers;

import com.semicolon.itaxi.data.models.Trip;
import com.semicolon.itaxi.dto.requests.BookTripRequest;
import com.semicolon.itaxi.dto.requests.LoginUserRequest;
import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.requests.TripHistoryRequest;
import com.semicolon.itaxi.dto.response.*;
import com.semicolon.itaxi.exceptions.*;
import com.semicolon.itaxi.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/iTaxi/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid @NotNull RegisterUserRequest request) throws MismatchedPasswordException, UserExistException {
        RegisterUserResponse userDto = userService.register(request);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status("Success")
                .message("User created ")
                .data(userDto)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }
    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginUserRequest request) throws InvalidUserException {
        LoginUserResponse response = userService.login(request);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status("Okay")
                .message("Welcome Back")
                .data(response)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/orderRide")
    public ResponseEntity<?> bookARide (@RequestBody @Valid @NotNull BookTripRequest request) throws NoDriverFoundException, UserExistException {
        log.info("Order a ride request ===> {}", request);
        BookTripResponse driverDto = userService.bookARide(request);
        ApiResponse apiResponse = ApiResponse.builder()
                .status("success")
                .message("A driver as been found")
                .data(driverDto)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @GetMapping("/userTripHistory/{email}")
    public List<Trip> getHistoryOfAllTrips(@PathVariable String email)throws NoTripHistoryForUserException{
        return userService.getHistoryOfAllTrips(email);
    }
}
