package com.semicolon.itaxi.services;

import com.semicolon.itaxi.dto.requests.RegisterDriverRequest;
import com.semicolon.itaxi.dto.response.RegisterDriverResponse;

public interface DriverService{
    RegisterDriverResponse register(RegisterDriverRequest request);
}
