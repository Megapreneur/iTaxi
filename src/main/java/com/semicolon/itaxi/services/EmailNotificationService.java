package com.semicolon.itaxi.services;

import com.semicolon.itaxi.dto.requests.EmailNotificationRequest;
import com.semicolon.itaxi.dto.requests.NotificationRequest;
import com.semicolon.itaxi.dto.requests.UserDto;

import javax.mail.MessagingException;

public interface EmailNotificationService {
    String sendHtmlMail(EmailNotificationRequest emailNotification) throws MessagingException;
    String send(NotificationRequest notificationRequest);
    void sendWelcomeMessage(UserDto userDto, String token);
    void sendResetPasswordMail(String email, String token);
}
