package com.semicolon.itaxi.dto.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserRequest {
    private String email;
    @JsonIgnore
    private String password;
}
