package com.cashier.stripe.application.service;

import com.cashier.stripe.domain.model.Customer;
import com.cashier.stripe.domain.port.in.CustomerUseCase;
import com.cashier.stripe.domain.port.out.CustomerRepository;
import com.cashier.stripe.domain.port.out.StripeGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerService implements CustomerUseCase {

    private final CustomerRepository customerRepository;
    private final StripeGateway stripeGateway;

    public CustomerService(CustomerRepository customerRepository, StripeGateway stripeGateway) {
        this.customerRepository = customerRepository;
        this.stripeGateway = stripeGateway;
    }

    @Override
    public Mono<Customer> createCustomer(Customer customer) {
        return stripeGateway.createStripeCustomer(customer)
                .flatMap(stripeId -> {
                    customer.setStripeCustomerId(stripeId);
                    return customerRepository.save(customer);
                });
    }

    @Override
    public Mono<Customer> getCustomer(Long customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public Mono<Customer> updateCustomer(Long customerId, Customer customer) {
        return customerRepository.findById(customerId)
                .flatMap(existing -> {
                    existing.setEmail(customer.getEmail());
                    existing.setName(customer.getName());
                    return stripeGateway.updateStripeCustomer(existing.getStripeCustomerId(), existing)
                            .then(customerRepository.save(existing));
                });
    }

    @Override
    public Mono<Void> deleteCustomer(Long customerId) {
        return customerRepository.delete(customerId);
    }
}
