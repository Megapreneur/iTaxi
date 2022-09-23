package com.semicolon.itaxi.dto.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterVehicleRequest {
    private String email;
    private String carNumber;
    private String carModel;
    private String carColour;
}
