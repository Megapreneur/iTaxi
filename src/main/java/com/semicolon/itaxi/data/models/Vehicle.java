package com.semicolon.itaxi.data.models;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "cars")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String vehicleId;
    private String model;
    private String vehicleNumber;
    private String color;
//    private String nameOfDriver;



}
