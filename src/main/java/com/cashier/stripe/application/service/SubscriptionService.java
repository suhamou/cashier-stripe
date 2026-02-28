package com.cashier.stripe.application.service;

import com.cashier.stripe.domain.model.Subscription;
import com.cashier.stripe.domain.port.in.SubscriptionUseCase;
import com.cashier.stripe.domain.port.out.CustomerRepository;
import com.cashier.stripe.domain.port.out.StripeGateway;
import com.cashier.stripe.domain.port.out.SubscriptionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SubscriptionService implements SubscriptionUseCase {

    private final SubscriptionRepository subscriptionRepository;
    private final CustomerRepository customerRepository;
    private final StripeGateway stripeGateway;

    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                                CustomerRepository customerRepository,
                                StripeGateway stripeGateway) {
        this.subscriptionRepository = subscriptionRepository;
        this.customerRepository = customerRepository;
        this.stripeGateway = stripeGateway;
    }

    @Override
    public Mono<Subscription> createSubscription(Long customerId, String priceId) {
        return customerRepository.findById(customerId)
                .flatMap(customer -> stripeGateway.createSubscription(customer.getStripeCustomerId(), priceId))
                .flatMap(stripeSubId -> {
                    Subscription subscription = Subscription.builder()
                            .customerId(customerId)
                            .stripeSubscriptionId(stripeSubId)
                            .status("active")
                            .build();
                    return subscriptionRepository.save(subscription);
                });
    }

    @Override
    public Mono<Subscription> getSubscription(Long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId);
    }

    @Override
    public Mono<Subscription> cancelSubscription(Long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId)
                .flatMap(subscription -> stripeGateway.cancelSubscription(subscription.getStripeSubscriptionId())
                        .then(Mono.defer(() -> {
                            subscription.setStatus("canceled");
                            return subscriptionRepository.save(subscription);
                        })));
    }

    @Override
    public Mono<Subscription> resumeSubscription(Long subscriptionId) {
        // Only paused subscriptions can be resumed; canceled subscriptions require a new subscription.
        return subscriptionRepository.findById(subscriptionId)
                .flatMap(subscription -> {
                    if ("canceled".equals(subscription.getStatus())) {
                        return Mono.error(new IllegalStateException(
                                "Cannot resume a canceled subscription. Create a new subscription instead."));
                    }
                    subscription.setStatus("active");
                    return subscriptionRepository.save(subscription);
                });
    }
}
