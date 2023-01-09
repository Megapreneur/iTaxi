package com.semicolon.itaxi.dto.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semicolon.itaxi.data.models.enums.Gender;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDriverRequest {
    private String name;
    private String address;
    @Email
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private Gender gender;
    @Size(message = "password can not be less than 8 characters", min = 8)
    private String password;
    private String confirmPassword;
}
