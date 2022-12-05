package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.models.Person;
import com.semicolon.itaxi.dto.requests.LoginUserRequest;
import com.semicolon.itaxi.dto.response.LoginUserResponse;

public interface PersonService {
    LoginUserResponse login(LoginUserRequest request);

    Person getPersonByUsername(String email);
}
