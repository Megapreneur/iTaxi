package com.semicolon.itaxi.dto.requests;

import com.semicolon.itaxi.data.models.enums.PaymentType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
    private String email;
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;
    private BigInteger amount;
}
