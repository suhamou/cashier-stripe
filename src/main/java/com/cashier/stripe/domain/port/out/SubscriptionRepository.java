package com.cashier.stripe.domain.port.out;

import com.cashier.stripe.domain.model.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubscriptionRepository {

    Mono<Subscription> save(Subscription subscription);

    Mono<Subscription> findById(Long subscriptionId);

    Flux<Subscription> findByCustomerId(Long customerId);

    Mono<Subscription> findByStripeSubscriptionId(String stripeSubscriptionId);

    Mono<Void> delete(Long subscriptionId);
}
