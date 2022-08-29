package com.semicolon.itaxi.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TripHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String pickUpAddress;
    private String dropOffAddress;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driverDetails;
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime time = LocalDateTime.now();

}
