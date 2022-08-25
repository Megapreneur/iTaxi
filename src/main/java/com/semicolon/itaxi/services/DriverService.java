package com.semicolon.itaxi.services;

import com.semicolon.itaxi.dto.requests.LoginDriverRequest;
import com.semicolon.itaxi.dto.requests.PaymentRequest;
import com.semicolon.itaxi.dto.requests.RegisterDriverRequest;
import com.semicolon.itaxi.dto.response.BookingResponse;
import com.semicolon.itaxi.dto.response.DriverDto;
import com.semicolon.itaxi.dto.response.PaymentResponse;
import com.semicolon.itaxi.exceptions.InvalidDriverException;
import com.semicolon.itaxi.exceptions.NoDriverFoundException;

public interface DriverService{
    DriverDto register(RegisterDriverRequest request);

    DriverDto getDriver(String location) throws NoDriverFoundException;

    DriverDto login(LoginDriverRequest request) throws InvalidDriverException;
    BookingResponse bookingDetails();
    PaymentResponse payment(PaymentRequest request);


}
