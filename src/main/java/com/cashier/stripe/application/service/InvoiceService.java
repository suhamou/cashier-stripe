package com.cashier.stripe.application.service;

import com.cashier.stripe.domain.model.Invoice;
import com.cashier.stripe.domain.port.in.InvoiceUseCase;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class InvoiceService implements InvoiceUseCase {

    @Override
    public Mono<Invoice> getInvoice(Long invoiceId) {
        return Mono.empty();
    }

    @Override
    public Flux<Invoice> listInvoices(Long customerId) {
        return Flux.empty();
    }
}
