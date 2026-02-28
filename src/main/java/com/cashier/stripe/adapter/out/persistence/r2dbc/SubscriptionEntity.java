package com.cashier.stripe.adapter.out.persistence.r2dbc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("subscriptions")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionEntity {

    @Id
    private Long subscriptionId;
    private Long customerId;
    private String stripeSubscriptionId;
    private String status;
    private String type;
    private Integer quantity;
    private LocalDateTime trialEndsAt;
    private LocalDateTime endsAt;
}
