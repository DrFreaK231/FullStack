package com.example.demo.Controller;

import com.example.demo.Entity.Dto.OrderDto;
import com.example.demo.Entity.Dto.PaymentDto;
import com.example.demo.Entity.Payment;
import com.example.demo.Service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/payment")

public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public String createPayment(@RequestBody OrderDto dto) {
        Payment payment = paymentService.createPayment(dto);
        return payment.getOrderSessionId();
    }

    @PostMapping("/update")
    public ResponseEntity<?> confirmPayment(@RequestBody PaymentDto dto) {
        Payment payment = paymentService.updatePayment(dto);
        return ResponseEntity.ok(payment.toResponse());
    }
}

