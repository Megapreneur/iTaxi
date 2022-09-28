package com.semicolon.itaxi.dto.requests;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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
