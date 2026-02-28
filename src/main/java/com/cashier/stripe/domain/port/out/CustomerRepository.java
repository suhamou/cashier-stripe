package com.cashier.stripe.domain.port.out;

import com.cashier.stripe.domain.model.Customer;
import reactor.core.publisher.Mono;

public interface CustomerRepository {

    Mono<Customer> save(Customer customer);

    Mono<Customer> findById(Long customerId);

    Mono<Customer> findByStripeCustomerId(String stripeCustomerId);

    Mono<Void> delete(Long customerId);
}
