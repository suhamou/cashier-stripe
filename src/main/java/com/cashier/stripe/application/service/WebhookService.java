package com.cashier.stripe.application.service;

import com.cashier.stripe.domain.model.Subscription;
import com.cashier.stripe.domain.port.in.WebhookUseCase;
import com.cashier.stripe.domain.port.out.StripeGateway;
import com.cashier.stripe.domain.port.out.SubscriptionRepository;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class WebhookService implements WebhookUseCase {

    private final StripeGateway stripeGateway;
    private final SubscriptionRepository subscriptionRepository;

    public WebhookService(StripeGateway stripeGateway, SubscriptionRepository subscriptionRepository) {
        this.stripeGateway = stripeGateway;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Mono<Void> handleWebhook(String payload, String sigHeader) {
        return stripeGateway.constructEvent(payload, sigHeader)
                .flatMap(this::processEvent);
    }

    private Mono<Void> processEvent(Event event) {
        String eventType = event.getType();
        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();

        return switch (eventType) {
            case "customer.subscription.updated" -> handleSubscriptionUpdated(deserializer);
            case "customer.subscription.deleted" -> handleSubscriptionDeleted(deserializer);
            case "invoice.payment_succeeded" -> handleInvoicePaymentSucceeded(deserializer);
            case "invoice.payment_failed" -> handleInvoicePaymentFailed(deserializer);
            default -> Mono.empty();
        };
    }

    private Mono<Void> handleSubscriptionUpdated(EventDataObjectDeserializer deserializer) {
        return deserializer.getObject()
                .map(obj -> (com.stripe.model.Subscription) obj)
                .map(stripeSub -> subscriptionRepository.findByStripeSubscriptionId(stripeSub.getId())
                        .flatMap(sub -> {
                            sub.setStatus(stripeSub.getStatus());
                            return subscriptionRepository.save(sub);
                        })
                        .then())
                .orElse(Mono.empty());
    }

    private Mono<Void> handleSubscriptionDeleted(EventDataObjectDeserializer deserializer) {
        return deserializer.getObject()
                .map(obj -> (com.stripe.model.Subscription) obj)
                .map(stripeSub -> subscriptionRepository.findByStripeSubscriptionId(stripeSub.getId())
                        .flatMap(sub -> {
                            sub.setStatus("canceled");
                            return subscriptionRepository.save(sub);
                        })
                        .then())
                .orElse(Mono.empty());
    }

    private Mono<Void> handleInvoicePaymentSucceeded(EventDataObjectDeserializer deserializer) {
        return Mono.empty();
    }

    private Mono<Void> handleInvoicePaymentFailed(EventDataObjectDeserializer deserializer) {
        return Mono.empty();
    }
}
