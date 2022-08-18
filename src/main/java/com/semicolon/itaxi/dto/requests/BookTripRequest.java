package com.semicolon.itaxi.dto.requests;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTripRequest {
    private String pickUpAddress;
    private String dropOffAddress;
    private String email;
}
