package com.semicolon.itaxi.data.models;

import com.semicolon.itaxi.data.models.enums.PaymentType;
import lombok.*;
import javax.persistence.Id;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id
    private String id;
    private PaymentType paymentType;
    private Long amount;
}
