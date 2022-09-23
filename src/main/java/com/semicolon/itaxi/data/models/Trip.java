package com.semicolon.itaxi.data.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String pickUpAddress;
    private String dropOffAddress;
    private String location;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime time = LocalDateTime.now();

}
