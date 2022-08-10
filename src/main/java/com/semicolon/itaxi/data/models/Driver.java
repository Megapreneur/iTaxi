package com.semicolon.itaxi.data.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Drivers")
@Builder
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String name;
    @NonNull
    private String address;
    @NonNull
    private String email;
    @NonNull
    private String phoneNumber;
    private String carNumber;
    private String carType;
    private String carColour;
}
