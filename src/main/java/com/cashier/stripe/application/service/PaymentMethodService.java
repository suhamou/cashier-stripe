package com.cashier.stripe.application.service;

import com.cashier.stripe.domain.model.PaymentMethod;
import com.cashier.stripe.domain.port.in.PaymentMethodUseCase;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PaymentMethodService implements PaymentMethodUseCase {

    @Override
    public Mono<PaymentMethod> addPaymentMethod(Long customerId, String stripePaymentMethodId) {
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .customerId(customerId)
                .stripeId(stripePaymentMethodId)
                .isDefault(false)
                .build();
        return Mono.just(paymentMethod);
    }

    @Override
    public Mono<PaymentMethod> getDefaultPaymentMethod(Long customerId) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> removePaymentMethod(Long customerId, Long paymentMethodId) {
        return Mono.empty();
    }
}
