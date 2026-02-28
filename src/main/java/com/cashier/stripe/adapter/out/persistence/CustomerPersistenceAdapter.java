package com.cashier.stripe.adapter.out.persistence;

import com.cashier.stripe.adapter.out.persistence.r2dbc.CustomerEntity;
import com.cashier.stripe.adapter.out.persistence.r2dbc.CustomerR2dbcRepository;
import com.cashier.stripe.domain.model.Customer;
import com.cashier.stripe.domain.port.out.CustomerRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomerPersistenceAdapter implements CustomerRepository {

    private final CustomerR2dbcRepository r2dbcRepository;

    public CustomerPersistenceAdapter(CustomerR2dbcRepository r2dbcRepository) {
        this.r2dbcRepository = r2dbcRepository;
    }

    @Override
    public Mono<Customer> save(Customer customer) {
        CustomerEntity entity = toEntity(customer);
        return r2dbcRepository.save(entity).map(this::toDomain);
    }

    @Override
    public Mono<Customer> findById(Long customerId) {
        return r2dbcRepository.findById(customerId).map(this::toDomain);
    }

    @Override
    public Mono<Customer> findByStripeCustomerId(String stripeCustomerId) {
        return r2dbcRepository.findByStripeCustomerId(stripeCustomerId).map(this::toDomain);
    }

    @Override
    public Mono<Void> delete(Long customerId) {
        return r2dbcRepository.deleteById(customerId);
    }

    private CustomerEntity toEntity(Customer customer) {
        return CustomerEntity.builder()
                .customerId(customer.getCustomerId())
                .stripeCustomerId(customer.getStripeCustomerId())
                .email(customer.getEmail())
                .name(customer.getName())
                .build();
    }

    private Customer toDomain(CustomerEntity entity) {
        return Customer.builder()
                .customerId(entity.getCustomerId())
                .stripeCustomerId(entity.getStripeCustomerId())
                .email(entity.getEmail())
                .name(entity.getName())
                .build();
    }
}
