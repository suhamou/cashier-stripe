package com.cashier.stripe.adapter.out.persistence.r2dbc;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CustomerR2dbcRepository extends ReactiveCrudRepository<CustomerEntity, Long> {

    Mono<CustomerEntity> findByStripeCustomerId(String stripeCustomerId);
}
