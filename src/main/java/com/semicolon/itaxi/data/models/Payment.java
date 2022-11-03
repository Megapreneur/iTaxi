package com.semicolon.itaxi.data.models;

import com.semicolon.itaxi.data.models.enums.PaymentType;
import lombok.*;
import javax.persistence.*;
import java.math.BigInteger;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;
    private BigInteger amount;
    @OneToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
}
