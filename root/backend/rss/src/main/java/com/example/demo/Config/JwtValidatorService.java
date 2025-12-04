package com.example.demo.Config;


import com.example.common.dto.RegisterRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class JwtValidatorService {

    private static final String SECRET = "b7e4f9a1c3d5e7f9b1a2c3d4e5f6a7b8";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public RegisterRequest getUserData(String token) {
        Claims claims = validateToken(token);
        RegisterRequest req = new RegisterRequest();
        req.setUsername((String) claims.get("username"));
        req.setEmail((String) claims.get("email"));
        req.setPassword("Oauth");
        return req;
    }
}
