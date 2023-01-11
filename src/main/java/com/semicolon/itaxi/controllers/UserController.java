package com.semicolon.itaxi.controllers;

import com.semicolon.itaxi.data.models.Trip;
import com.semicolon.itaxi.dto.requests.*;
import com.semicolon.itaxi.dto.response.*;
import com.semicolon.itaxi.exceptions.*;
import com.semicolon.itaxi.services.PersonService;
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
@RequestMapping("/api/v1/iTaxi")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;
    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody @Valid @NotNull RegisterUserRequest request) throws MismatchedPasswordException, UserExistException, InvalidEmailException {
        RegisterUserResponse userDto = userService.register(request);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status("Success")
                .message("User created ")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }
    @PostMapping("/user/verify?token")
    public ResponseEntity<?>verifyUser(@RequestParam String token) throws ITaxiException {
        userService.verifyUser(token);
        ApiResponse apiResponse = ApiResponse.builder()
                .status("Okay")
                .message("Verification is successful")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);

    }
    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginUserRequest request) throws InvalidUserException {
        LoginUserResponse response = personService.login(request);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status("Okay")
                .message("Welcome Back")
                .data(response)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/user/orderRide")
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
    @GetMapping("/user/userTripHistory/{email}")
    public List<Trip> getHistoryOfAllTrips(@PathVariable String email)throws NoTripHistoryForUserException{
        return userService.getHistoryOfAllTrips(email);
    }

    @PostMapping("/user/payment")
    public PaymentResponse makePayment(@RequestBody PaymentRequest request) throws NoTripHistoryForUserException {
        return userService.makePayment(request);
    }
}
