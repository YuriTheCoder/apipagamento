package com.example.payments.service;

import com.example.payments.domain.Payment;
import com.example.payments.domain.PaymentStatus;
import com.example.payments.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Payment createPayment(String externalId, BigDecimal amount, String currency, String description) {
        paymentRepository.findByExternalId(externalId).ifPresent(p -> {
            throw new IllegalArgumentException("externalId já existe");
        });
        Payment p = new Payment();
        p.setExternalId(externalId);
        p.setAmount(amount);
        p.setCurrency(currency.toUpperCase());
        p.setDescription(description);
        p.setStatus(PaymentStatus.PENDING);
        return paymentRepository.save(p);
    }

    @Transactional(readOnly = true)
    public List<Payment> listAll() {
        return paymentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Payment getByExternalId(String externalId) {
        return paymentRepository.findByExternalId(externalId)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado"));
    }

    @Transactional
    public Payment updateStatus(String externalId, PaymentStatus status) {
        Payment p = getByExternalId(externalId);
        p.setStatus(status);
        return paymentRepository.save(p);
    }

    @Transactional
    public Payment refund(String externalId, BigDecimal amount, String reason) {
        Payment p = getByExternalId(externalId);
        if (p.getStatus() != PaymentStatus.CAPTURED && p.getStatus() != PaymentStatus.AUTHORIZED) {
            throw new IllegalStateException("Somente pagamentos autorizados/capturados podem ser estornados");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0 || amount.compareTo(p.getAmount()) > 0) {
            throw new IllegalArgumentException("Valor de estorno inválido");
        }
        p.setStatus(PaymentStatus.REFUNDED);
        return paymentRepository.save(p);
    }
}


