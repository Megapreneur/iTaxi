package com.semicolon.itaxi.services;

import com.semicolon.itaxi.dto.requests.EmailNotificationRequest;
import com.semicolon.itaxi.dto.requests.NotificationRequest;

import javax.mail.MessagingException;

public interface EmailNotificationService {
    String sendHtmlMail(EmailNotificationRequest emailNotification) throws MessagingException;
    String send(NotificationRequest notificationRequest);
}
