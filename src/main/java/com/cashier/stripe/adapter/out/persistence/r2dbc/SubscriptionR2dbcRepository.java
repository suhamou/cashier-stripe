package com.cashier.stripe.adapter.out.persistence.r2dbc;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubscriptionR2dbcRepository extends ReactiveCrudRepository<SubscriptionEntity, Long> {

    Flux<SubscriptionEntity> findByCustomerId(Long customerId);

    Mono<SubscriptionEntity> findByStripeSubscriptionId(String stripeSubscriptionId);
}
