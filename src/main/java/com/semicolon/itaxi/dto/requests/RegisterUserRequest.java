package com.semicolon.itaxi.dto.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semicolon.itaxi.data.models.enums.Gender;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {
    private String name;
    @Email
    @Column(unique = true)
    private String email;
    private String phoneNumber;
    private String address;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String confirmPassword;
    private Gender gender;
}
