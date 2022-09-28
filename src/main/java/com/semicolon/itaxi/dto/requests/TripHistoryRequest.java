package com.semicolon.itaxi.dto.requests;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripHistoryRequest {
    private String email;
}
