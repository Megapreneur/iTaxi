package com.semicolon.itaxi.dto.requests;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDriverRequest {
    private String email;
    private String location;
}
