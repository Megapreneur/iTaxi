package com.semicolon.itaxi.data.models;

import com.semicolon.itaxi.data.models.enums.DriverStatus;
import com.semicolon.itaxi.data.models.enums.Gender;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Drivers")
@Validated
@Builder
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private String address;
    @Valid
    @Email
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String location;
    private String password;
    private String confirmPassword;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    @Enumerated(value = EnumType.STRING)
    private DriverStatus driverStatus;


}
