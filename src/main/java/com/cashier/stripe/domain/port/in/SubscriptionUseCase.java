package com.cashier.stripe.domain.port.in;

import com.cashier.stripe.domain.model.Subscription;
import reactor.core.publisher.Mono;

public interface SubscriptionUseCase {

    Mono<Subscription> createSubscription(Long customerId, String priceId);

    Mono<Subscription> getSubscription(Long subscriptionId);

    Mono<Subscription> cancelSubscription(Long subscriptionId);

    Mono<Subscription> resumeSubscription(Long subscriptionId);
}
