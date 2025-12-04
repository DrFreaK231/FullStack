package com.example.demo.Service;

import com.example.demo.Entity.Payment;
import com.example.demo.Repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment savePaypalPayment(
            String sessionId,
            String paypalOrderId,
            String captureId,
            String payerId,
            String payerEmail,
            String amount,
            String currency,
            String status
    ) {

        // Avoid duplicate save (PayPal orderId is unique)
        Optional<Payment> existing = repository.findByPaypalOrderId(paypalOrderId);
        if (existing.isPresent()) {
            return existing.get();
        }

        Payment payment = new Payment();
        payment.setSessionId(sessionId);
        payment.setPaypalOrderId(paypalOrderId);
        payment.setCaptureId(captureId);
        payment.setPayerId(payerId);
        payment.setPayerEmail(payerEmail);
        payment.setAmount(new BigDecimal(amount));
        payment.setCurrency(currency);
        payment.setStatus(status);
        return repository.save(payment);
    }
}
