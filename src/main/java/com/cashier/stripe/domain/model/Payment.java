package com.cashier.stripe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    private Long paymentId;
    private String stripePaymentIntentId;
    private Long amount;
    private String currency;
    private String status;
}
