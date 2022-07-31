package com.semicolon.itaxi.services;

import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.response.RegisterUserResponse;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest request);
}
