package com.semicolon.itaxi.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public User(@NonNull String name, @NonNull String email, @NonNull String phoneNumber, @NonNull String address, String password, Gender gender) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
        this.gender = gender;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String name;
    @NonNull
    @Column(unique = true)
    private String email;
    @NonNull
    @Column(unique = true)
    private String phoneNumber;
    @NonNull
    private String address;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String confirmPassword;

    private String pickUpAddress;
    private String dropOffAddress;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private LocalDateTime dateOfRide = LocalDateTime.now();
}
