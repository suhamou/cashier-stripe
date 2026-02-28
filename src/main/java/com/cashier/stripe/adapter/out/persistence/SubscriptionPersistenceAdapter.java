package com.cashier.stripe.adapter.out.persistence;

import com.cashier.stripe.adapter.out.persistence.r2dbc.SubscriptionEntity;
import com.cashier.stripe.adapter.out.persistence.r2dbc.SubscriptionR2dbcRepository;
import com.cashier.stripe.domain.model.Subscription;
import com.cashier.stripe.domain.port.out.SubscriptionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SubscriptionPersistenceAdapter implements SubscriptionRepository {

    private final SubscriptionR2dbcRepository r2dbcRepository;

    public SubscriptionPersistenceAdapter(SubscriptionR2dbcRepository r2dbcRepository) {
        this.r2dbcRepository = r2dbcRepository;
    }

    @Override
    public Mono<Subscription> save(Subscription subscription) {
        return r2dbcRepository.save(toEntity(subscription)).map(this::toDomain);
    }

    @Override
    public Mono<Subscription> findById(Long subscriptionId) {
        return r2dbcRepository.findById(subscriptionId).map(this::toDomain);
    }

    @Override
    public Flux<Subscription> findByCustomerId(Long customerId) {
        return r2dbcRepository.findByCustomerId(customerId).map(this::toDomain);
    }

    @Override
    public Mono<Subscription> findByStripeSubscriptionId(String stripeSubscriptionId) {
        return r2dbcRepository.findByStripeSubscriptionId(stripeSubscriptionId).map(this::toDomain);
    }

    @Override
    public Mono<Void> delete(Long subscriptionId) {
        return r2dbcRepository.deleteById(subscriptionId);
    }

    private SubscriptionEntity toEntity(Subscription subscription) {
        return SubscriptionEntity.builder()
                .subscriptionId(subscription.getSubscriptionId())
                .customerId(subscription.getCustomerId())
                .stripeSubscriptionId(subscription.getStripeSubscriptionId())
                .status(subscription.getStatus())
                .type(subscription.getType())
                .quantity(subscription.getQuantity())
                .trialEndsAt(subscription.getTrialEndsAt())
                .endsAt(subscription.getEndsAt())
                .build();
    }

    private Subscription toDomain(SubscriptionEntity entity) {
        return Subscription.builder()
                .subscriptionId(entity.getSubscriptionId())
                .customerId(entity.getCustomerId())
                .stripeSubscriptionId(entity.getStripeSubscriptionId())
                .status(entity.getStatus())
                .type(entity.getType())
                .quantity(entity.getQuantity())
                .trialEndsAt(entity.getTrialEndsAt())
                .endsAt(entity.getEndsAt())
                .build();
    }
}
