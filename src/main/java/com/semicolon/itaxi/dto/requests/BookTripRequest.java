package com.semicolon.itaxi.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTripRequest {
    private String pickUpAddress;
    private String dropOffAddress;
}
