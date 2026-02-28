package com.cashier.stripe.application.service;

import com.cashier.stripe.domain.model.Customer;
import com.cashier.stripe.domain.model.Subscription;
import com.cashier.stripe.domain.port.out.CustomerRepository;
import com.cashier.stripe.domain.port.out.StripeGateway;
import com.cashier.stripe.domain.port.out.SubscriptionRepository;
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
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private StripeGateway stripeGateway;

    private SubscriptionService subscriptionService;

    @BeforeEach
    void setUp() {
        subscriptionService = new SubscriptionService(subscriptionRepository, customerRepository, stripeGateway);
    }

    @Test
    void createSubscription_shouldCreateSubscription() {
        Customer customer = Customer.builder()
                .customerId(1L)
                .stripeCustomerId("cus_test123")
                .build();

        Subscription savedSubscription = Subscription.builder()
                .subscriptionId(1L)
                .customerId(1L)
                .stripeSubscriptionId("sub_test123")
                .status("active")
                .build();

        when(customerRepository.findById(1L)).thenReturn(Mono.just(customer));
        when(stripeGateway.createSubscription("cus_test123", "price_123")).thenReturn(Mono.just("sub_test123"));
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(Mono.just(savedSubscription));

        StepVerifier.create(subscriptionService.createSubscription(1L, "price_123"))
                .expectNextMatches(s -> s.getSubscriptionId() == 1L
                        && "sub_test123".equals(s.getStripeSubscriptionId())
                        && "active".equals(s.getStatus()))
                .verifyComplete();
    }

    @Test
    void cancelSubscription_shouldCancelSubscription() {
        Subscription subscription = Subscription.builder()
                .subscriptionId(1L)
                .stripeSubscriptionId("sub_test123")
                .status("active")
                .build();

        Subscription canceledSubscription = Subscription.builder()
                .subscriptionId(1L)
                .stripeSubscriptionId("sub_test123")
                .status("canceled")
                .build();

        when(subscriptionRepository.findById(1L)).thenReturn(Mono.just(subscription));
        when(stripeGateway.cancelSubscription("sub_test123")).thenReturn(Mono.empty());
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(Mono.just(canceledSubscription));

        StepVerifier.create(subscriptionService.cancelSubscription(1L))
                .expectNextMatches(s -> "canceled".equals(s.getStatus()))
                .verifyComplete();
    }

    @Test
    void getSubscription_shouldReturnSubscription() {
        Subscription subscription = Subscription.builder()
                .subscriptionId(1L)
                .status("active")
                .build();

        when(subscriptionRepository.findById(1L)).thenReturn(Mono.just(subscription));

        StepVerifier.create(subscriptionService.getSubscription(1L))
                .expectNext(subscription)
                .verifyComplete();
    }
}
