package com.semicolon.itaxi.dto.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semicolon.itaxi.data.models.enums.Gender;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

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
    @Size(message = "password can not be less than 8 characters", min = 8)
    private String password;
    private String confirmPassword;
    private Gender gender;
}
