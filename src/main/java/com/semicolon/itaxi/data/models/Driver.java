package com.semicolon.itaxi.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semicolon.itaxi.data.models.enums.Gender;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Drivers")
@Validated
@Builder
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    private String address;
    @Valid
    @Email
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String carNumber;
    private String carType;
    private String carColour;
//    @Getter(AccessLevel.PRIVATE)
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String confirmPassword;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    @OneToOne
    @JoinColumn(name = "vehicle_vehicle_id")
    private Vehicle vehicle;
    private String location;


}
