package com.cashier.stripe.adapter.in.web;

import com.cashier.stripe.domain.port.in.WebhookUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/webhooks")
public class WebhookController {

    private final WebhookUseCase webhookUseCase;

    public WebhookController(WebhookUseCase webhookUseCase) {
        this.webhookUseCase = webhookUseCase;
    }

    @PostMapping("/stripe")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> handleStripeWebhook(@RequestBody String payload,
                                           @RequestHeader("Stripe-Signature") String sigHeader) {
        return webhookUseCase.handleWebhook(payload, sigHeader);
    }
}
