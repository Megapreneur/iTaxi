package com.semicolon.itaxi.controllers;

import com.semicolon.itaxi.dto.requests.RegisterDriverRequest;
import com.semicolon.itaxi.services.DriverService;
import com.sun.istack.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {
    private DriverService driverService;

    public DriverController(DriverService driverService){
        this.driverService = driverService;
    }
    @PostMapping("/")
    public ResponseEntity<?>createDriver(@RequestBody @Valid @NotNull RegisterDriverRequest registerDriverRequest){

    }
}
