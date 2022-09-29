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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String address;
    private String password;
    private String confirmPassword;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

}
