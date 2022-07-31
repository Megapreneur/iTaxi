package com.semicolon.itaxi.dto.requests;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class BookTripRequest {
    @NonNull
    private String pickUpAddress;
    @NonNull
    private String dropOffAddress;
    private String email;
}
