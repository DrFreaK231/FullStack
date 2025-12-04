package com.example.demo.Service;

import com.example.demo.Entity.Dto.OrderDto;
import com.example.demo.Entity.Dto.PaymentDto;
import com.example.demo.Entity.Enums.PaymentProvider;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.Payment;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Payment createPayment(OrderDto dto) {

        Order order = orderRepository.findByUuid(dto.getOrderSessionId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();
        payment.setAmount(dto.getAmount());
        payment.setCurrency(dto.getCurrency());
        payment.setStatus("PENDING");
        payment.setCreatedAt(LocalDateTime.now());
        payment.setOrder(order);
        payment.setOrderSessionId(dto.getOrderSessionId());

        orderRepository.save(order);
        paymentRepository.save(payment);

        return payment;
    }

    @Transactional
    public Payment updatePayment(PaymentDto dto) {

        Payment payment = paymentRepository.findByOrderSessionId(dto.getOrderSessionId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setProvider(PaymentProvider.PAYPAL);
        payment.setStatus("SUCCESS");
        payment.setUpdatedAt(LocalDateTime.now());
        payment.setPaymentId(dto.getPaymentId());
        payment.setPayerId(dto.getPayerId());

        Order order = payment.getOrder();
        order.setUpdatedAt(LocalDateTime.now());
        order.setStatus("PAID");


        // Save parent — cascade saves payment as well
        orderRepository.save(order);

        return payment;
    }

    public Payment getPayment(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}
