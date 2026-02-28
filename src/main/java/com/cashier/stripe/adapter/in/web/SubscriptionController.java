package com.cashier.stripe.adapter.in.web;

import com.cashier.stripe.domain.model.Subscription;
import com.cashier.stripe.domain.port.in.SubscriptionUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionUseCase subscriptionUseCase;

    public SubscriptionController(SubscriptionUseCase subscriptionUseCase) {
        this.subscriptionUseCase = subscriptionUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Subscription> createSubscription(@RequestParam Long customerId,
                                                  @RequestParam String priceId) {
        return subscriptionUseCase.createSubscription(customerId, priceId);
    }

    @GetMapping("/{subscriptionId}")
    public Mono<Subscription> getSubscription(@PathVariable Long subscriptionId) {
        return subscriptionUseCase.getSubscription(subscriptionId);
    }

    @DeleteMapping("/{subscriptionId}")
    public Mono<Subscription> cancelSubscription(@PathVariable Long subscriptionId) {
        return subscriptionUseCase.cancelSubscription(subscriptionId);
    }

    @PostMapping("/{subscriptionId}/resume")
    public Mono<Subscription> resumeSubscription(@PathVariable Long subscriptionId) {
        return subscriptionUseCase.resumeSubscription(subscriptionId);
    }
}
