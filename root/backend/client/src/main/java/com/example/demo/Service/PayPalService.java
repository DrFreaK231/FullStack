package com.example.demo.Service;

import com.example.demo.Config.PayPalClient;
import com.example.demo.Entity.Dto.OrderDto;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class PayPalService {

    private final PayPalClient payPalClient;

    public PayPalService(PayPalClient payPalClient) {
        this.payPalClient = payPalClient;
    }

    public Map<String, Object> createOrder(OrderDto cartItem, String sessionId) throws IOException {

        String currency = cartItem.getCurrency();
        double totalAmount = cartItem.getAmount() * cartItem.getQuantity();

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext()
                .returnUrl("http://localhost:8080/paypal/success?sessionId=" + sessionId)
                .cancelUrl("http://localhost:8080/paypal/cancel");

        Item paypalItem = new Item()
                .name("Product " + cartItem.getProductUuid())
                .unitAmount(new Money()
                        .currencyCode(currency)
                        .value(String.format("%.2f", cartItem.getAmount())))
                .quantity(String.valueOf(cartItem.getQuantity()));

        PurchaseUnitRequest purchaseUnit = new PurchaseUnitRequest()
                .customId(sessionId)
                .amountWithBreakdown(new AmountWithBreakdown()
                        .currencyCode(currency)
                        .value(String.format("%.2f", totalAmount))
                        .amountBreakdown(new AmountBreakdown()
                                .itemTotal(new Money()
                                        .currencyCode(currency)
                                        .value(String.format("%.2f", totalAmount)))))
                .items(List.of(paypalItem));

        orderRequest.applicationContext(applicationContext);
        orderRequest.purchaseUnits(List.of(purchaseUnit));

        OrdersCreateRequest request = new OrdersCreateRequest().requestBody(orderRequest);
        request.prefer("return=representation");

        HttpResponse<Order> response = payPalClient.payPalHttpClient().execute(request);
        Order order = response.result();

        String approveUrl = order.links().stream()
                .filter(l -> "approve".equals(l.rel()))
                .findFirst()
                .get()
                .href();

        return Map.of(
                "sessionId", sessionId,
                "approveUrl", approveUrl,
                "paypalOrderId", order.id()
        );
    }
}
