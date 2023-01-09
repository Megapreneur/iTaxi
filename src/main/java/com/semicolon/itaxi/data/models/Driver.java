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
public class Driver extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String location;
    private boolean isEnabled;
    @Enumerated(value = EnumType.STRING)
    private DriverStatus driverStatus;
}
