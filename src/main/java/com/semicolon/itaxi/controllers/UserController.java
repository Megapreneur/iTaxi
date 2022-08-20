package com.semicolon.itaxi.controllers;

import com.semicolon.itaxi.dto.requests.BookTripRequest;
import com.semicolon.itaxi.dto.requests.LoginUserRequest;
import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.response.*;
import com.semicolon.itaxi.exceptions.NoDriverFoundException;
import com.semicolon.itaxi.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public RegisterUserResponse register(@RequestBody RegisterUserRequest request){
        return userService.register(request);
    }
    @PostMapping("/login")
    public LoginUserResponse login (@RequestBody LoginUserRequest request){
        return userService.login(request);
    }

    @GetMapping("/orderRide")
    public ResponseEntity<?> bookARide (@RequestBody @Valid @NotNull BookTripRequest request) throws NoDriverFoundException {
        log.info("Order a ride request ===> {}", request);
        BookTripResponse driverDto = userService.bookARide(request);
        ApiResponse apiResponse = ApiResponse.builder()
                .status("success")
                .message("A driver as been found")
                .data(driverDto)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }
}
