package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.models.enums.Gender;
import com.semicolon.itaxi.data.repositories.UserRepository;
import com.semicolon.itaxi.dto.requests.BookTripRequest;
import com.semicolon.itaxi.dto.requests.LoginUserRequest;
import com.semicolon.itaxi.dto.requests.RegisterUserRequest;
import com.semicolon.itaxi.dto.response.BookTripResponse;
import com.semicolon.itaxi.dto.response.LoginUserResponse;
import com.semicolon.itaxi.dto.response.RegisterUserResponse;
import com.semicolon.itaxi.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    public void testThatAUserCanBeAdded() throws MismatchedPasswordException, UserExistException, InvalidEmailException {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("John Emeka");
        request.setEmail("je@gmail.com");
        request.setGender(Gender.valueOf("MALE"));
        request.setPhoneNumber("09183643421");
        request.setAddress("49, Johnson street");
        request.setPassword("password");
        request.setConfirmPassword("password");
        RegisterUserResponse response = userService.register(request);
        assertEquals("Hello John Emeka , Your registration was successful", response.getMessage());
    }
    @Test
    public  void  testThatARegisteredUserCanLogin() throws InvalidUserException {
        LoginUserRequest request = new LoginUserRequest();
        request.setEmail("je@gmail.com");
        request.setPassword("password");
        LoginUserResponse response = userService.login(request);
        assertEquals("Welcome back John Emeka. Where will you like to go today?", response.getMessage());
    }
    @Test
    public void testThatAUserCanOrderARide() throws UserExistException, NoDriverFoundException {
        BookTripRequest request = new BookTripRequest();
        request.setPickUpAddress("Juno");
        request.setLocation("sabo");
        request.setEmail("ademolamegbabi@gmail.com");
        request.setDropOffAddress("VGC");
        BookTripResponse response = userService.bookARide(request);
        assertEquals("", response.getMessage());
    }

}