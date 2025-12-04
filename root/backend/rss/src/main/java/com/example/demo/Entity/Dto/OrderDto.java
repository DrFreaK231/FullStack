package com.example.demo.Entity.Dto;

public class OrderDto {
    private String email;
    private String productUuid;
    private int quantity;
    private float amount;
    private String currency;
    private String orderSessionId;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductUuid() {
        return productUuid;
    }

    public void setProductUuid(String productUuid) {
        this.productUuid = productUuid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getOrderSessionId() {
        return orderSessionId;
    }

    public void setOrderSessionId(String orderSessionId) {
        this.orderSessionId = orderSessionId;
    }
}