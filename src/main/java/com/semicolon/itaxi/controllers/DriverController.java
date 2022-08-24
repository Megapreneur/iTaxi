package com.semicolon.itaxi.controllers;

import com.semicolon.itaxi.dto.requests.LoginDriverRequest;
import com.semicolon.itaxi.dto.requests.RegisterDriverRequest;
import com.semicolon.itaxi.dto.response.ApiResponse;
import com.semicolon.itaxi.dto.response.DriverDto;
import com.semicolon.itaxi.exceptions.InvalidDriverException;
import com.semicolon.itaxi.services.DriverService;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/iTaxi/drivers")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService){
        this.driverService = driverService;
    }
    @PostMapping("/register")
    public ResponseEntity<?>createDriver(@RequestBody @Valid @NotNull RegisterDriverRequest registerDriverRequest) {
        log.info("Account Creation Request ==> {}", registerDriverRequest);
        DriverDto driverDto = driverService.register(registerDriverRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .status("success")
                .message("Driver created successfully")
                .data(driverDto)
                .build();
        log.info("Returning response");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @PatchMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid @NotNull LoginDriverRequest request) throws InvalidDriverException {
        DriverDto driverDto = driverService.login(request);
        ApiResponse apiResponse = ApiResponse.builder()
                .status("Success")
                .message("Welcome Back " + driverDto.getName())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);

    }
}
