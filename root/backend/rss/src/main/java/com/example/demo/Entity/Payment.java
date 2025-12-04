package com.example.demo.Entity;

import com.example.demo.Entity.Enums.PaymentProvider;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentProvider provider;
    private String paymentId;
    private String payerId;
    private float amount;
    private String currency;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Column( updatable = false)
    private String orderSessionId;

    @OneToOne
    @JoinColumn(name = "order_id") // foreign key in payment table
    private Order order;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentProvider getProvider() {
        return provider;
    }

    public void setProvider(PaymentProvider provider) {
        this.provider = provider;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOrderSessionId() {
        return orderSessionId;
    }

    public void setOrderSessionId(String orderSessionId) {
        this.orderSessionId = orderSessionId;
    }

    public Map<String, Object> toResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("id", this.id);
        response.put("paymentId", this.paymentId);
        response.put("status", this.status);
        response.put("amount", this.amount);
        response.put("currency", this.currency);
        response.put("provider", this.provider.toString());
        response.put("orderId", this.order != null ? this.order.getId() : null);
        response.put("updatedAt", this.updatedAt.toString());
        return response;
    }
}
