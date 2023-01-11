package com.semicolon.itaxi.services;

import com.semicolon.itaxi.dto.requests.*;

import javax.mail.MessagingException;

public interface EmailNotificationService {
//    String sendHtmlMail(EmailNotificationRequest emailNotification) throws MessagingException;
//    String send(NotificationRequest notificationRequest);
    void sendWelcomeMessageToCustomer(RegisterUserRequest userDto, String token);
    void sendWelcomeMessageToDriver(RegisterDriverRequest driverDto, String token);
    void sendResetPasswordMail(String email, String token);

    void newTokenMail(String email, String token);
}
