package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.models.Person;
import com.semicolon.itaxi.dto.requests.LoginUserRequest;
import com.semicolon.itaxi.dto.response.LoginUserResponse;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService{
    @Override
    public LoginUserResponse login(LoginUserRequest request) {
        return null;
    }

    @Override
    public Person getPersonByUsername(String email) {
        return null;
    }
}
