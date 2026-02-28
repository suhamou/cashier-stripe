package com.cashier.stripe.domain.port.in;

import reactor.core.publisher.Mono;

public interface WebhookUseCase {

    Mono<Void> handleWebhook(String payload, String sigHeader);
}
