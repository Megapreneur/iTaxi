package com.semicolon.itaxi.services;

import com.semicolon.itaxi.Mapper;
import com.semicolon.itaxi.data.models.User;
import com.semicolon.itaxi.data.repositories.UserRepository;
import com.semicolon.itaxi.dto.requests.BookTripRequest;
import com.semicolon.itaxi.dto.requests.LoginUserRequest;
import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.response.BookTripResponse;
import com.semicolon.itaxi.dto.response.LoginUserResponse;
import com.semicolon.itaxi.dto.response.RegisterUserResponse;
import com.semicolon.itaxi.exceptions.IncorrectPasswordException;
import com.semicolon.itaxi.exceptions.InvalidUserException;
import com.semicolon.itaxi.exceptions.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public RegisterUserResponse register(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())){throw  new UserExistException("Email Already Exist");
        }
        User user = new User();
        Mapper.map(request, user);
        User savedUser = userRepository.save(user);
        RegisterUserResponse response = new RegisterUserResponse();
        Mapper.map(savedUser, response);
        return response;
    }

    @Override
    public LoginUserResponse login(LoginUserRequest request) {
        Optional<User> savedUser = userRepository.findByEmail(request.getEmail());
        if (savedUser.isPresent()){
            if (savedUser.get().getPassword().equals(request.getPassword())){
                LoginUserResponse response = new LoginUserResponse();
                response.setMessage("Welcome back " + savedUser.get().getName() + ". Where will you like to go today?");
                return response;
            }
            throw new IncorrectPasswordException("Incorrect Password");
        }
        throw new InvalidUserException("Invalid Email");
    }

    @Override
    public BookTripResponse book(BookTripRequest request) {

        return null;
    }
}
