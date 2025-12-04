package com.example.demo.Service;

import com.example.demo.Entity.*;
import com.example.demo.Entity.Dto.OrderItemDto;
import com.example.demo.Repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrderItemService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderItemService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;

    }

    @Transactional
    public Order addItemToOrder(Long orderId, OrderItemDto dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());
        order.addItem(item);

        float total = order.getItems().stream()
                .map(i -> i.getProduct().getPrice() * i.getQuantity())
                .reduce(0f, Float::sum);

        order.setAmount(total);
        order.setUpdatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }
}
