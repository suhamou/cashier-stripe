package com.cashier.stripe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    private Long invoiceId;
    private Long customerId;
    private String stripeInvoiceId;
    private Long total;
    private String status;
    private LocalDateTime createdAt;
}
