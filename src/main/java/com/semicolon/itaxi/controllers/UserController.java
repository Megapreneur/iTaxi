package com.semicolon.itaxi.controllers;

import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.response.RegisterUserResponse;
import com.semicolon.itaxi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/iTaxi")
public class UserController {
    @Autowired
    private UserService service;
    @PostMapping("/register")
    public RegisterUserResponse register(@RequestBody RegisterUserRequest request){
        return service.register(request);
    }
}
