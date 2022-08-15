package com.semicolon.itaxi.data.models;

import com.semicolon.itaxi.data.models.enums.PaymentType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;


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
    private Long amount;
}
