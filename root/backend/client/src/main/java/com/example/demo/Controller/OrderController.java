package com.example.demo.Controller;


import com.example.demo.Config.JwtService;
import com.example.demo.Entity.Dto.OrderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final WebClient webClient;
    private final JwtService jwtService;

    public OrderController(JwtService jwtService) {
        this.jwtService = jwtService;
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:9000/api/orders")
                .build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(HttpServletRequest request, @RequestBody OrderDto dto) {
        String cookieHeader = request.getHeader(HttpHeaders.COOKIE);
        String jwtToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName()) || "auth_jwt".equals(cookie.getName())) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }
        if (jwtToken != null) {

            Map<String, Object> userData = jwtService.getUserData(jwtToken);
            System.out.println(userData.get("username"));

            dto.setEmail((String) userData.get("username"));
        }



        String response = webClient
                .post()
                .uri("/create")
                .header(HttpHeaders.COOKIE, cookieHeader != null ? cookieHeader : "")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(String.class)
                .block();



        return ResponseEntity.ok(Map.of("orderUuid", response));

    }

    @GetMapping
    public ResponseEntity<?> getOrder(HttpServletRequest request) {
        String cookieHeader = request.getHeader(HttpHeaders.COOKIE);
        String response = webClient
                .get()
                .uri("")
                .header(HttpHeaders.COOKIE, cookieHeader != null ? cookieHeader : "")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(response, Object.class);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to parse JSON");
        }
    }
}
