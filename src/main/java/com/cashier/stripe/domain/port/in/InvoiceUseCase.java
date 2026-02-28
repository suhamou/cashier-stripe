package com.cashier.stripe.domain.port.in;

import com.cashier.stripe.domain.model.Invoice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InvoiceUseCase {

    Mono<Invoice> getInvoice(Long invoiceId);

    Flux<Invoice> listInvoices(Long customerId);
}
