package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.models.Driver;
import com.semicolon.itaxi.data.models.Trip;
import com.semicolon.itaxi.dto.requests.*;
import com.semicolon.itaxi.dto.response.*;
import com.semicolon.itaxi.exceptions.*;

import java.util.List;

public interface DriverService{
    RegisterDriverResponse register(RegisterDriverRequest request) throws MismatchedPasswordException, UserExistException, InvalidEmailException;
    DriverDto getDriver(String location) throws NoDriverFoundException;
    RegisterVehicleResponse registerVehicle(RegisterVehicleRequest request) throws InvalidDriverException, InvalidActionException;
    LoginDriverResponse login(LoginDriverRequest request) throws InvalidDriverException, IncorrectPasswordException;
    List<Trip> getHistoryOfAllTrips(String email) throws NoTripHistoryForUserException;
    BookingResponse bookingDetails();
    PaymentResponse payment(PaymentRequest request);
}
