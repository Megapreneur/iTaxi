package com.semicolon.itaxi.services;

import com.semicolon.itaxi.dto.requests.RegisterDriverRequest;
import com.semicolon.itaxi.dto.response.RegisterDriverResponse;
import org.springframework.stereotype.Service;

@Service

public class DriverServiceImpl implements DriverService{

    @Override
    public RegisterDriverResponse register(RegisterDriverRequest request) {
        return null;
    }
}
