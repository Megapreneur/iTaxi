package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.models.Trip;
import com.semicolon.itaxi.dto.requests.*;
import com.semicolon.itaxi.dto.response.*;
import com.semicolon.itaxi.exceptions.*;

import java.util.List;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest request) throws MismatchedPasswordException, UserExistException;
    LoginUserResponse login(LoginUserRequest request) throws InvalidUserException;
    BookTripResponse bookARide(BookTripRequest request) throws NoDriverFoundException, UserExistException;
    List<Trip>getHistoryOfAllTrips(TripHistoryRequest request) throws NoTripHistoryForUserException;
    PaymentResponse makePayment(PaymentRequest paymentRequest);
    UserResponse feedback(String message);
}
