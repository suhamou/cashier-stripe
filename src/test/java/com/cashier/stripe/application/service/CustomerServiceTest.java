package com.cashier.stripe.application.service;

import com.cashier.stripe.domain.model.Customer;
import com.cashier.stripe.domain.port.out.CustomerRepository;
import com.cashier.stripe.domain.port.out.StripeGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private StripeGateway stripeGateway;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepository, stripeGateway);
    }

    @Test
    void createCustomer_shouldCreateAndSaveCustomer() {
        Customer customer = Customer.builder()
                .email("test@example.com")
                .name("Test User")
                .build();

        when(stripeGateway.createStripeCustomer(any(Customer.class))).thenReturn(Mono.just("cus_test123"));
        when(customerRepository.save(any(Customer.class))).thenReturn(Mono.just(
                Customer.builder()
                        .customerId(1L)
                        .stripeCustomerId("cus_test123")
                        .email("test@example.com")
                        .name("Test User")
                        .build()
        ));

        StepVerifier.create(customerService.createCustomer(customer))
                .expectNextMatches(c -> c.getCustomerId() == 1L
                        && "cus_test123".equals(c.getStripeCustomerId())
                        && "test@example.com".equals(c.getEmail()))
                .verifyComplete();
    }

    @Test
    void getCustomer_shouldReturnCustomer() {
        Customer expected = Customer.builder()
                .customerId(1L)
                .email("test@example.com")
                .build();

        when(customerRepository.findById(1L)).thenReturn(Mono.just(expected));

        StepVerifier.create(customerService.getCustomer(1L))
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void deleteCustomer_shouldDeleteCustomer() {
        when(customerRepository.delete(1L)).thenReturn(Mono.empty());

        StepVerifier.create(customerService.deleteCustomer(1L))
                .verifyComplete();
    }
}
