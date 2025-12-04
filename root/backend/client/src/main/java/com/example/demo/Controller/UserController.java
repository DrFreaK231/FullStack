package com.example.demo.Controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/user")
public class UserController {



    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:9000/api/user")
            .build();




    @GetMapping("/register")
    public ResponseEntity<?> register(
            HttpServletRequest request
    ) {
        String cookieHeader = request.getHeader(HttpHeaders.COOKIE);
        String body = webClient.get()
                .uri("/register")
                .header(HttpHeaders.COOKIE, cookieHeader)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return ResponseEntity.ok(body);
    }



}
