package com.example.payments.dto;

import com.example.payments.domain.Payment;
import com.example.payments.domain.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;

public class PaymentResponse {
    public Long id;
    public String externalId;
    public BigDecimal amount;
    public String currency;
    public PaymentStatus status;
    public String description;
    public Instant createdAt;
    public Instant updatedAt;

    public static PaymentResponse from(Payment payment) {
        PaymentResponse r = new PaymentResponse();
        r.id = payment.getId();
        r.externalId = payment.getExternalId();
        r.amount = payment.getAmount();
        r.currency = payment.getCurrency();
        r.status = payment.getStatus();
        r.description = payment.getDescription();
        r.createdAt = payment.getCreatedAt();
        r.updatedAt = payment.getUpdatedAt();
        return r;
    }
}


