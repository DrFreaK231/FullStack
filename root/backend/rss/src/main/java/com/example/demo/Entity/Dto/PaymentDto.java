package com.example.demo.Entity.Dto;

import com.example.demo.Entity.Enums.PaymentProvider;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaymentDto {
    private String provider;
    private String paymentId;
    private String payerId;
    private float amount;
    private String currency;
    private Long orderId;
    private String orderSessionId;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderSessionId() {
        return orderSessionId;
    }

    public void setOrderSessionId(String orderSessionId) {
        this.orderSessionId = orderSessionId;
    }

    public PaymentDto(String provider, String paymentId, String payerId, float amount, String currency, Long orderId, String orderSessionId) {
        this.provider = provider;
        this.paymentId = paymentId;
        this.payerId = payerId;
        this.amount = amount;
        this.currency = currency;
        this.orderId = orderId;
        this.orderSessionId = orderSessionId;
    }
}
