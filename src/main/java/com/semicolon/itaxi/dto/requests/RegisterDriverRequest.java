package com.semicolon.itaxi.dto.requests;

import com.semicolon.itaxi.data.models.enums.Gender;
import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDriverRequest {
    private String name;
    @NonNull
    private String address;
    @NotNull
    private String email;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String carNumber;
    @NonNull
    private String carType;
    @NonNull
    private String carColour;
    @NotNull
    private Gender gender;

}
