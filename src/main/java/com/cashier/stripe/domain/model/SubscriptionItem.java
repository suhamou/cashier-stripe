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
public class SubscriptionItem {

    private Long itemId;
    private Long subscriptionId;
    private String stripeId;
    private String stripeProduct;
    private String stripePrice;
    private Integer quantity;
}
