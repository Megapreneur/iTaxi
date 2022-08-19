package com.semicolon.itaxi.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.semicolon.itaxi.data.models.enums.Gender;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto{

    private String message;
//    private Integer id;
    private String name;
    private String phoneNumber;
    private String model;
    private String vehicleNumber;
    private String color;
//    private String email;
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.SCALAR, pattern = "dd-MM-yyyy")
//    private LocalDate dob;
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.SCALAR, pattern = "dd-MM-yyyy")
//    private LocalDate dateJoined;


}
