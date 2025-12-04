package com.example.demo.Controller.Payment;

import com.example.demo.Config.PayPalClient;
import com.example.demo.Entity.Dto.PaymentDto;
import com.example.demo.Entity.Payment;
import com.example.demo.Service.PaymentService;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Map;

@RestController
public class PayPalSuccessController {

    private final PayPalClient payPalClient;
    private final PaymentService paymentService;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:9000/api/payment")
            .build();


    public PayPalSuccessController(PayPalClient payPalClient, PaymentService paymentService) {
        this.payPalClient = payPalClient;
        this.paymentService = paymentService;
    }

    @GetMapping("/paypal/success")
    public void captureOrder(
            @RequestParam("token") String token,
            @RequestParam("sessionId") String sessionId,
            HttpServletResponse response,
            HttpServletRequest httpServletRequest

    ) throws IOException {
        System.out.println(sessionId);
        try {
            OrdersCaptureRequest request = new OrdersCaptureRequest(token);
            request.requestBody(new OrderRequest());

            HttpResponse<Order> httpResponse = payPalClient.payPalHttpClient().execute(request);
            Order order = httpResponse.result();

            String paypalOrderId = order.id();
            String payerId = order.payer() != null ? order.payer().payerId() : null;
            String payerEmail = order.payer() != null ? order.payer().email() : null;

            String amount = null;
            String currency = null;
            String captureId = null;

            if (!order.purchaseUnits().isEmpty()
                    && order.purchaseUnits().get(0).payments() != null
                    && !order.purchaseUnits().get(0).payments().captures().isEmpty()) {

                Capture capture = order.purchaseUnits().get(0).payments().captures().get(0);

                captureId = capture.id();
                amount = capture.amount().value();
                currency = capture.amount().currencyCode();
            }
            PaymentDto dto = new PaymentDto();
            paymentService.savePaypalPayment(
                    sessionId,
                    paypalOrderId,
                    captureId,
                    payerId,
                    payerEmail,
                    amount,
                    currency,
                    order.status()
            );
            dto.setPayerId(payerId);
            dto.setPaymentId(captureId);
            dto.setOrderSessionId(sessionId );

            String cookieHeader = httpServletRequest.getHeader(HttpHeaders.COOKIE);
             webClient
                    .post()
                    .uri("/update")
                    .header(HttpHeaders.COOKIE, cookieHeader != null ? cookieHeader : "")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();


            response.sendRedirect("http://localhost:3000/payment/success?sessionId=" + sessionId);

        } catch (Exception e) {
            e.printStackTrace(); // <<< print the real exception
            response.sendRedirect("http://localhost:3000/payment/failed");
        }
    }

    @GetMapping("/paypal/cancel")
    public Map<String, String> cancel() {
        return Map.of("message", "Payment cancelled by user.");
    }
}
