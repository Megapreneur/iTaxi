package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.models.Person;
import com.semicolon.itaxi.dto.requests.LoginUserRequest;
import com.semicolon.itaxi.dto.response.LoginUserResponse;
import com.semicolon.itaxi.exceptions.ITaxiException;

public interface PersonService {
    LoginUserResponse login(LoginUserRequest request);
    void verifyUser(String token) throws ITaxiException;
    void verifyDriver(String token) throws ITaxiException;

    void forgetPassword(String email) throws ITaxiException;
    void verifyForgetPassword(String token, String password);


    Person getPersonByUsername(String email);
}
