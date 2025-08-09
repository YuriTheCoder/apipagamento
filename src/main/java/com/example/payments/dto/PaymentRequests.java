package com.example.payments.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class PaymentRequests {

    public static class CreatePaymentRequest {
        @NotBlank
        public String externalId;

        @NotNull
        @DecimalMin(value = "0.01")
        public BigDecimal amount;

        @NotBlank
        @Size(min = 3, max = 3)
        public String currency;

        @Size(max = 255)
        public String description;
    }

    public static class UpdateStatusRequest {
        @NotBlank
        public String status; // PENDING, AUTHORIZED, CAPTURED, FAILED, CANCELED, REFUNDED
    }

    public static class RefundRequest {
        @NotNull
        @DecimalMin(value = "0.01")
        public BigDecimal amount;
        @Size(max = 255)
        public String reason;
    }
}


