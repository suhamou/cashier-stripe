package com.cashier.stripe.domain.port.in;

import com.cashier.stripe.domain.model.PaymentMethod;
import reactor.core.publisher.Mono;

public interface PaymentMethodUseCase {

    Mono<PaymentMethod> addPaymentMethod(Long customerId, String stripePaymentMethodId);

    Mono<PaymentMethod> getDefaultPaymentMethod(Long customerId);

    Mono<Void> removePaymentMethod(Long customerId, Long paymentMethodId);
}
