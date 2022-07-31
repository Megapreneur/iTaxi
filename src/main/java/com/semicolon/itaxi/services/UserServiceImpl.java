package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.repositories.UserRepository;
import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.response.RegisterUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public RegisterUserResponse register(RegisterUserRequest request) {
        return null;
    }
}
