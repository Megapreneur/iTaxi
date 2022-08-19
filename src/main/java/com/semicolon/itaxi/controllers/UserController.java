package com.semicolon.itaxi.controllers;

import com.semicolon.itaxi.dto.requests.BookTripRequest;
import com.semicolon.itaxi.dto.requests.LoginUserRequest;
import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.response.BookTripResponse;
import com.semicolon.itaxi.dto.response.LoginUserResponse;
import com.semicolon.itaxi.dto.response.RegisterUserResponse;
import com.semicolon.itaxi.exceptions.NoDriverFoundException;
import com.semicolon.itaxi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService service;
    @PostMapping("/register")
    public RegisterUserResponse register(@RequestBody RegisterUserRequest request){
        return service.register(request);
    }
    @PostMapping("/login")
    public LoginUserResponse login (@RequestBody LoginUserRequest request){
        return service.login(request);
    }
    @GetMapping("/orderRide")
    public BookTripResponse bookARide (@RequestBody BookTripRequest request) throws NoDriverFoundException {
        return service.bookARide(request);

    }
}
