package com.semicolon.itaxi.services;

import com.semicolon.itaxi.dto.requests.*;


public interface EmailNotificationService {
    void sendWelcomeMessageToCustomer(RegisterUserRequest userDto, String token);
    void sendWelcomeMessageToDriver(RegisterDriverRequest driverDto, String token);
    void sendResetPasswordMail(String email, String token);

    void newTokenMail(String email, String token);
}
