package com.example.demo.Controller.Payment;

import com.example.demo.Entity.Dto.OrderDto;
import com.example.demo.Service.PayPalService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/paypal")
public class PayPalController {
//
    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:9000/api/payment")
            .build();

    private final PayPalService payPalService;

    public PayPalController(PayPalService payPalService) {
        this.payPalService = payPalService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto dto , HttpServletRequest httpServletRequest) {
        try {

            Map<String, Object> paypalResult = payPalService.createOrder(dto, dto.getOrderSessionId());
            String cookieHeader = httpServletRequest.getHeader(HttpHeaders.COOKIE);
            String response = webClient
                    .post()
                    .uri("/create")
                    .header(HttpHeaders.COOKIE, cookieHeader != null ? cookieHeader : "")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return ResponseEntity.ok(paypalResult);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of("error", e.getMessage())
            );
        }
    }

}
