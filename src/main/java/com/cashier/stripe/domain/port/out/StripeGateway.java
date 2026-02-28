package com.cashier.stripe.domain.port.out;

import com.cashier.stripe.domain.model.Customer;
import com.cashier.stripe.domain.model.Subscription;
import com.stripe.model.Event;
import reactor.core.publisher.Mono;

public interface StripeGateway {

    Mono<String> createStripeCustomer(Customer customer);

    Mono<Void> updateStripeCustomer(String stripeCustomerId, Customer customer);

    Mono<String> createSubscription(String stripeCustomerId, String priceId);

    Mono<Void> cancelSubscription(String stripeSubscriptionId);

    Mono<String> createPaymentIntent(Long amount, String currency, String stripeCustomerId);

    Mono<Event> constructEvent(String payload, String sigHeader);
}
