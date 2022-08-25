package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.models.User;
import com.semicolon.itaxi.data.repositories.UserRepository;
import com.semicolon.itaxi.dto.requests.BookTripRequest;
import com.semicolon.itaxi.dto.requests.LoginUserRequest;
import com.semicolon.itaxi.dto.requests.PaymentRequest;
import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.response.*;
import com.semicolon.itaxi.exceptions.IncorrectPasswordException;
import com.semicolon.itaxi.exceptions.InvalidUserException;
import com.semicolon.itaxi.exceptions.NoDriverFoundException;
import com.semicolon.itaxi.exceptions.UserExistException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private DriverService driverService;



    @Override
    public RegisterUserResponse register(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))throw  new UserExistException("Email Already Exist");

        User user = new User(request.getName(), request.getEmail(), request.getPhoneNumber(), request.getAddress(),
                request.getPassword(), request.getGender());
        if (request.getPassword().equals(request.getConfirmPassword())){
            User savedUser = userRepository.save(user);
            RegisterUserResponse response = new RegisterUserResponse();
            response.setMessage(savedUser.getName() + " your registration is successful");
            return response;
        }else {
            throw new IncorrectPasswordException("Password does not match");
        }
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
    public BookTripResponse bookARide(BookTripRequest request) throws NoDriverFoundException {
        Optional<User> savedUser = userRepository.findByEmail(request.getEmail());
        if (savedUser.isPresent()){
            savedUser.get().setPickUpAddress(request.getPickUpAddress());
            savedUser.get().setDropOffAddress(request.getDropOffAddress());
            BookTripResponse response = new BookTripResponse();
            response.setMessage("You have been connected to " + driverService.getDriver(request.getLocation())
                    +". Your trip from " + savedUser.get().getPickUpAddress() + " to "
                    + savedUser.get().getDropOffAddress() + " was ordered at " + response.getDateOfRide());
            return response ;
        }
        throw new InvalidUserException("Invalid Email") ;
    }

    @Override
    public PaymentResponse makePayment(PaymentRequest paymentRequest) {
        Optional<User> savedUser = userRepository.findByEmail(paymentRequest.getEmail());
        if (savedUser.isPresent()){


        }
        return null;
    }

    @Override
    public UserResponse feedback(String message) {

        return null;
    }

}
