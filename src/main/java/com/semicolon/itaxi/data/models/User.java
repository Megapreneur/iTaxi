package com.semicolon.itaxi.data.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.semicolon.itaxi.data.models.enums.Gender;
import com.semicolon.itaxi.data.models.enums.PaymentType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String name;
    @NonNull
    @Column(unique = true)
    private String email;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String address;
    private String password;
    private String pickUpAddress;
    private String dropOffAddress;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
    private LocalDateTime dateOfRide = LocalDateTime.now();
}
