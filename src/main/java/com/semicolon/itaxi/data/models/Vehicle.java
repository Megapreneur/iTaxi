package com.semicolon.itaxi.data.models;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("cars")
public class Vehicle {
    private String model;
    private String vehicleNumber;
    private String color;
    private String nameOfDriver;



}
