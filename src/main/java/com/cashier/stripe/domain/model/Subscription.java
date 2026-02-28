package com.cashier.stripe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {

    private Long subscriptionId;
    private Long customerId;
    private String stripeSubscriptionId;
    private String status;
    private String type;
    private Integer quantity;
    private LocalDateTime trialEndsAt;
    private LocalDateTime endsAt;
}
