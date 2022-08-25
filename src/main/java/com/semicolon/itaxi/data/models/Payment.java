package com.semicolon.itaxi.data.models;

import com.semicolon.itaxi.data.models.enums.PaymentType;
import com.semicolon.itaxi.services.UserService;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String id;
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private BigInteger amount;
}
