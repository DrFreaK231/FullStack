package com.example.demo.Service;


import com.example.demo.Entity.Dto.OrderDto;
import com.example.demo.Entity.Dto.PaymentDto;
import com.example.demo.Entity.Enums.PaymentProvider;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.Payment;
import com.example.demo.Entity.User;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.PaymentRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;


    public OrderService(OrderRepository orderRepository, UserRepository userRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Order createOrder(OrderDto dto){
        User user = userRepository.findByUsername(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));


        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setUser(user);
        order.setAmount(dto.getAmount());
        order.setCurrency(dto.getCurrency());
        order.setStatus("PENDING");

        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<PaymentDto> getAllOrders() {

        List<Order> orderList = orderRepository.findAll();

        List<PaymentDto> dtoList = new ArrayList<>();

        for (Order order : orderList) {
            Payment payment = paymentRepository.findByOrderSessionId(order.getUuid())
                    .orElseThrow(() -> new RuntimeException("Payment not found"));

            PaymentDto dto = new PaymentDto(
                    payment.getProvider().toString(),
                    payment.getPaymentId(), // paymentId (not needed now)
                    payment.getPayerId(), // payerId
                    order.getAmount(),
                    order.getCurrency(),
                    order.getId(),
                    payment.getOrderSessionId() // orderSessionId
            );

            dtoList.add(dto);
        }

        return dtoList;
    }





}
