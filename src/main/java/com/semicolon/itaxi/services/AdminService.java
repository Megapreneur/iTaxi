package com.semicolon.itaxi.services;

import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.response.RegisterUserResponse;
import com.semicolon.itaxi.exceptions.InvalidEmailException;
import com.semicolon.itaxi.exceptions.MismatchedPasswordException;
import com.semicolon.itaxi.exceptions.UserExistException;

public interface AdminService {
    RegisterUserResponse register(RegisterUserRequest request) throws MismatchedPasswordException, UserExistException, InvalidEmailException;
}
