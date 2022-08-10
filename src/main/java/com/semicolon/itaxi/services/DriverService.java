package com.semicolon.itaxi.services;

import com.semicolon.itaxi.dto.requests.RegisterDriverRequest;
import com.semicolon.itaxi.dto.response.DriverDto;

public interface DriverService{
    DriverDto register(RegisterDriverRequest request);
}
