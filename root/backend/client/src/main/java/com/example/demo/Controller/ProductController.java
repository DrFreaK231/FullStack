package com.example.demo.Controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.awt.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:9000/api/products")
            .build();

    @GetMapping
    public ResponseEntity<?> getProducts(HttpServletRequest request) {
        String cookieHeader = request.getHeader(HttpHeaders.COOKIE);

        String response = webClient.get()
                .uri("")
                .header(HttpHeaders.COOKIE, cookieHeader != null ? cookieHeader : "")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return ResponseEntity.ok(response);
    }

}
