package com.semicolon.itaxi;

import com.semicolon.itaxi.data.models.User;
import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.response.RegisterUserResponse;

public class Mapper {

    public static void map(RegisterUserRequest request, User user) {
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
    }

    public static void map(User savedUser, RegisterUserResponse response) {
        response.setMessage("Welcome " + savedUser.getName() + "to iTaxi. \nWe wish you safe, peaceful and fast trips");
    }
}
