package com.semicolon.itaxi.services;

import com.semicolon.itaxi.data.models.Driver;
import com.semicolon.itaxi.data.models.enums.DriverStatus;
import com.semicolon.itaxi.data.models.enums.Gender;
import com.semicolon.itaxi.dto.requests.LoginDriverRequest;
import com.semicolon.itaxi.dto.requests.RegisterDriverRequest;
import com.semicolon.itaxi.dto.requests.RegisterVehicleRequest;
import com.semicolon.itaxi.dto.response.LoginDriverResponse;
import com.semicolon.itaxi.dto.response.RegisterDriverResponse;
import com.semicolon.itaxi.dto.response.RegisterVehicleResponse;
import com.semicolon.itaxi.exceptions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DriverServiceImplTest {
    @Autowired
    private DriverService driverService;

    @Test
    public void testThatADriverCabBeRegistered() throws MismatchedPasswordException, UserExistException, InvalidEmailException {
        RegisterDriverRequest request = RegisterDriverRequest
                .builder()
                .name("Sunday Idowu")
                .address("13, ebutte street")
                .phoneNumber("08187687644")
                .gender(Gender.valueOf("MALE"))
                .email("si@gmail.com")
                .password("password")
                .confirmPassword("password")
                .build();
        RegisterDriverResponse response = driverService.register(request);
        assertEquals("Hello Sunday Idowu , Your registration was successful", response.getMessage());
    }

    @Test
    public void testThatARegisteredDriverCanLogin() throws InvalidDriverException, IncorrectPasswordException {
        LoginDriverRequest request = LoginDriverRequest
                .builder()
                .driverStatus(DriverStatus.valueOf("AVAILABLE"))
                .email("si@gmail.com")
                .location("Yaba")
                .password("password")
                .build();
        LoginDriverResponse response = driverService.login(request);
        assertEquals("Welcome back Sunday Idowu. Ready to go for some rides ?", response.getMessage());
    }

    @Test
    public void testThatARegisteredDriverCanRegisterHisCar() throws InvalidDriverException, InvalidActionException {
        RegisterVehicleRequest request = RegisterVehicleRequest
                .builder()
                .carColour("Gold")
                .carModel("Lexus")
                .carNumber("IKJ637NG")
                .email("si@gmail.com")
                .build();
        RegisterVehicleResponse response = driverService.registerVehicle(request);
        assertEquals("Sunday Idowu your car has been registered successfully. Safe trips", response.getMessage());
    }
    @Test
    public void testThatADriverCanBeGotten(){
        Driver driver = new Driver();
        driver.setLocation("yaba");


    }

}