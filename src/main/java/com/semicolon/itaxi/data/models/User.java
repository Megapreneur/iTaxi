package com.semicolon.itaxi.data.models;

import lombok.*;

import javax.persistence.*;

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
    private String email;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String address;
    private String password;
    private String pickUpAddress;
    private String dropOffAddress;
}
