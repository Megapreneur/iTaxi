package com.semicolon.itaxi.services;

import com.semicolon.itaxi.dto.requests.BookTripRequest;
import com.semicolon.itaxi.dto.requests.LoginUserRequest;
import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.response.BookTripResponse;
import com.semicolon.itaxi.dto.response.LoginUserResponse;
import com.semicolon.itaxi.dto.response.RegisterUserResponse;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest request);
    LoginUserResponse login(LoginUserRequest request);
    BookTripResponse book(BookTripRequest request);
}
