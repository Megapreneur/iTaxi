package com.semicolon.itaxi.services;

import com.semicolon.itaxi.dto.requests.BookTripRequest;
import com.semicolon.itaxi.dto.requests.LoginUserRequest;
import com.semicolon.itaxi.dto.requests.PaymentRequest;
import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.response.*;
import com.semicolon.itaxi.exceptions.InvalidUserException;
import com.semicolon.itaxi.exceptions.MismatchedPasswordException;
import com.semicolon.itaxi.exceptions.NoDriverFoundException;
import com.semicolon.itaxi.exceptions.UserExistException;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest request) throws MismatchedPasswordException, UserExistException;
    LoginUserResponse login(LoginUserRequest request) throws InvalidUserException;
    BookTripResponse bookARide(BookTripRequest request) throws NoDriverFoundException;
    PaymentResponse makePayment(PaymentRequest paymentRequest);
    UserResponse feedback(String message);
}
