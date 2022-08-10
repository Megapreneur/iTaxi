package com.semicolon.itaxi.dto.requests;

import lombok.NonNull;

public class RegisterDriverRequest {
    private String name;
    @NonNull
    private String address;
    @NonNull
    private String email;
    @NonNull
    private String phoneNumber;
    private String carNumber;
    private String carType;
    private String carColour;

}
