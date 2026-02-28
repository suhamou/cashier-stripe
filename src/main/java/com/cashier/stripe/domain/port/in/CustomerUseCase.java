package com.cashier.stripe.domain.port.in;

import com.cashier.stripe.domain.model.Customer;
import reactor.core.publisher.Mono;

public interface CustomerUseCase {

    Mono<Customer> createCustomer(Customer customer);

    Mono<Customer> getCustomer(Long customerId);

    Mono<Customer> updateCustomer(Long customerId, Customer customer);

    Mono<Void> deleteCustomer(Long customerId);
}
