package com.semicolon.itaxi.dto.requests;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookTripRequest {
    private String pickUpAddress;
    private String dropOffAddress;
    private String email;
    private String location;
}
