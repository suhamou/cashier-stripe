package com.cashier.stripe.adapter.out.stripe;

import com.cashier.stripe.config.StripeConfig;
import com.cashier.stripe.domain.model.Customer;
import com.cashier.stripe.domain.port.out.StripeGateway;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StripeGatewayAdapter implements StripeGateway {

    private final StripeConfig stripeConfig;

    public StripeGatewayAdapter(StripeConfig stripeConfig) {
        this.stripeConfig = stripeConfig;
        com.stripe.Stripe.apiKey = stripeConfig.getApiKey();
    }

    @Override
    public Mono<String> createStripeCustomer(Customer customer) {
        return Mono.fromCallable(() -> {
            Map<String, Object> params = new HashMap<>();
            params.put("email", customer.getEmail());
            params.put("name", customer.getName());
            com.stripe.model.Customer stripeCustomer = com.stripe.model.Customer.create(params);
            return stripeCustomer.getId();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> updateStripeCustomer(String stripeCustomerId, Customer customer) {
        return Mono.fromCallable(() -> {
            com.stripe.model.Customer stripeCustomer = com.stripe.model.Customer.retrieve(stripeCustomerId);
            Map<String, Object> params = new HashMap<>();
            params.put("email", customer.getEmail());
            params.put("name", customer.getName());
            stripeCustomer.update(params);
            return null;
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    @Override
    public Mono<String> createSubscription(String stripeCustomerId, String priceId) {
        return Mono.fromCallable(() -> {
            Map<String, Object> item = new HashMap<>();
            item.put("price", priceId);
            Map<String, Object> params = new HashMap<>();
            params.put("customer", stripeCustomerId);
            params.put("items", List.of(item));
            com.stripe.model.Subscription subscription = com.stripe.model.Subscription.create(params);
            return subscription.getId();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> cancelSubscription(String stripeSubscriptionId) {
        return Mono.fromCallable(() -> {
            com.stripe.model.Subscription subscription = com.stripe.model.Subscription.retrieve(stripeSubscriptionId);
            subscription.cancel();
            return null;
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    @Override
    public Mono<String> createPaymentIntent(Long amount, String currency, String stripeCustomerId) {
        return Mono.fromCallable(() -> {
            Map<String, Object> params = new HashMap<>();
            params.put("amount", amount);
            params.put("currency", currency);
            params.put("customer", stripeCustomerId);
            com.stripe.model.PaymentIntent paymentIntent = com.stripe.model.PaymentIntent.create(params);
            return paymentIntent.getId();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Event> constructEvent(String payload, String sigHeader) {
        return Mono.fromCallable(() ->
                Webhook.constructEvent(payload, sigHeader, stripeConfig.getWebhookSecret())
        ).subscribeOn(Schedulers.boundedElastic());
    }
}
