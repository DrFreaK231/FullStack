package com.example.demo.Controller.Oauth;


import com.example.common.dto.RegisterRequest;
import com.example.demo.Config.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;


    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8000/api/users")
            .build();

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request, HttpServletRequest httpServletRequest) {
        String cookieHeader = httpServletRequest.getHeader(HttpHeaders.COOKIE);
        String response = webClient.post()
                .uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .header(HttpHeaders.COOKIE, cookieHeader != null ? cookieHeader : "")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RegisterRequest request ) {


        ResponseEntity<String> response = webClient.post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchangeToMono(clientResponse -> {
                    HttpHeaders headers = new HttpHeaders();
                    clientResponse.headers().asHttpHeaders().forEach(headers::addAll);
                    Mono<String> body = clientResponse.bodyToMono(String.class);
                    return body.map(b -> new ResponseEntity<>(b, headers, clientResponse.statusCode()));
                })
                .block();
        return response;

    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return ResponseEntity.status(401).body("No cookies found");
        }

        String token = Arrays.stream(cookies)
                .filter(c -> "auth_jwt".equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
        if (token == null) {
            return ResponseEntity.status(401).body("JWT cookie not found");
        }
        try {
            Map<String, Object> userData = jwtService.getUserData(token);
            return ResponseEntity.ok(userData);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
    }



}
