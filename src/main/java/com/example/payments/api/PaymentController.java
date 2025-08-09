package com.example.payments.api;

import com.example.payments.domain.Payment;
import com.example.payments.domain.PaymentStatus;
import com.example.payments.dto.PaymentRequests;
import com.example.payments.dto.PaymentResponse;
import com.example.payments.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api/payments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Payments", description = "Endpoints para operações de pagamento")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Cria um novo pagamento")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentResponse> create(@Valid @RequestBody PaymentRequests.CreatePaymentRequest req) {
        Payment p = paymentService.createPayment(req.externalId, req.amount, req.currency, req.description);
        return ResponseEntity.created(URI.create("/api/payments/" + p.getExternalId()))
                .body(PaymentResponse.from(p));
    }

    @Operation(summary = "Lista todos os pagamentos")
    @GetMapping
    public List<PaymentResponse> list() {
        return paymentService.listAll().stream().map(PaymentResponse::from).toList();
    }

    @Operation(summary = "Busca pagamento por externalId")
    @GetMapping("/{externalId}")
    public PaymentResponse get(@PathVariable String externalId) {
        return PaymentResponse.from(paymentService.getByExternalId(externalId));
    }

    @Operation(summary = "Atualiza status do pagamento")
    @PatchMapping(path = "/{externalId}/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PaymentResponse updateStatus(@PathVariable String externalId, @Valid @RequestBody PaymentRequests.UpdateStatusRequest req) {
        PaymentStatus status = PaymentStatus.valueOf(req.status.toUpperCase());
        return PaymentResponse.from(paymentService.updateStatus(externalId, status));
    }

    @Operation(summary = "Solicita estorno do pagamento")
    @PostMapping(path = "/{externalId}/refund", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PaymentResponse refund(@PathVariable String externalId, @Valid @RequestBody PaymentRequests.RefundRequest req) {
        return PaymentResponse.from(paymentService.refund(externalId, req.amount, req.reason));
    }
}


