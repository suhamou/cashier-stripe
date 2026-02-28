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
public class PaymentMethod {

    private Long paymentMethodId;
    private Long customerId;
    private String stripeId;
    private String type;
    private Boolean isDefault;
}
