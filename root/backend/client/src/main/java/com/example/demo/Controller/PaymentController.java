package com.example.demo.Controller;

import com.example.demo.Entity.Dto.PaymentDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final WebClient webClient;

    public PaymentController() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:9000/api/payment")
                .build();
    }

    @PostMapping
    public ResponseEntity<?> createPayment(HttpServletRequest request, @RequestBody PaymentDto dto) {
        String cookieHeader = request.getHeader(HttpHeaders.COOKIE);

        String response = webClient
                .post()
                .uri("/create")
                .header(HttpHeaders.COOKIE, cookieHeader != null ? cookieHeader : "")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(String.class)
                .block();


        return ResponseEntity.ok(response);
    }
}
