package com.semicolon.itaxi.controllers;

import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.response.ApiResponse;
import com.semicolon.itaxi.dto.response.RegisterUserResponse;
import com.semicolon.itaxi.exceptions.InvalidEmailException;
import com.semicolon.itaxi.exceptions.MismatchedPasswordException;
import com.semicolon.itaxi.exceptions.UserExistException;
import com.semicolon.itaxi.services.AdminService;
import com.semicolon.itaxi.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/iTaxi")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("admin/register")
    public ResponseEntity<?> register(RegisterUserRequest request) throws MismatchedPasswordException, UserExistException, InvalidEmailException {
        RegisterUserResponse response = adminService.register(request);
        ApiResponse apiResponse = ApiResponse.builder()
                .status("Success")
                .message("")
                .data(response)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
