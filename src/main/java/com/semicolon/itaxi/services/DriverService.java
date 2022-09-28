package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.models.Driver;
import com.semicolon.itaxi.dto.requests.LoginDriverRequest;
import com.semicolon.itaxi.dto.requests.PaymentRequest;
import com.semicolon.itaxi.dto.requests.RegisterDriverRequest;
import com.semicolon.itaxi.dto.requests.RegisterVehicleRequest;
import com.semicolon.itaxi.dto.response.*;
import com.semicolon.itaxi.exceptions.*;

public interface DriverService{
    RegisterDriverResponse register(RegisterDriverRequest request) throws MismatchedPasswordException, UserExistException;

    Driver getDriver(String location) throws NoDriverFoundException;
    RegisterDriverResponse registerVehicle(RegisterVehicleRequest request) throws InvalidDriverException, InvalidActionException;

    LoginDriverResponse login(LoginDriverRequest request) throws InvalidDriverException, IncorrectPasswordException;
    BookingResponse bookingDetails();
    PaymentResponse payment(PaymentRequest request);


}
