package com.semicolon.itaxi.services;

import com.semicolon.itaxi.dto.requests.BookTripRequest;
import com.semicolon.itaxi.dto.requests.LoginUserRequest;
import com.semicolon.itaxi.dto.requests.PaymentRequest;
import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.response.*;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest request);
    LoginUserResponse login(LoginUserRequest request);
    BookTripResponse bookARide(BookTripRequest request);
    PaymentResponse makePayment(PaymentRequest paymentRequest);
    UserResponse feedback(String message);
}
