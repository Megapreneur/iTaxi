package com.semicolon.itaxi.data.repositories;

import com.semicolon.itaxi.data.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
