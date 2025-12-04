package com.example.demo.Config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private static final String SECRET = "b7e4f9a1c3d5e7f9b1a2c3d4e5f6a7b8";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(String email) {
        long expirationTime = 1000 * 60 * 60 * 24; // 1 day
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(String email, String username ,String provider) {
        Map<String, Object> claims = Map.of(
                "email", email,
                "username", username,
                "provider", provider
        );

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email) // main identifier, usually email
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(3600)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Map<String, Object> getUserData(String token) {
        Claims claims = validateToken(token);
        return Map.of(
                "email", claims.getOrDefault("email", "" ),
                "username", claims.getOrDefault("username", ""),
                "provider", claims.getOrDefault("provider", "")
        );
    }



}

