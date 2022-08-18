package com.semicolon.itaxi.dto.requests;

import com.semicolon.itaxi.data.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String password;
    private Gender gender;
}
