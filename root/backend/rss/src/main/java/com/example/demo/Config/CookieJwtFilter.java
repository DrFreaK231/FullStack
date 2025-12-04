package com.example.demo.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class CookieJwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtValidatorService jwtValidatorService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("access_token".equals(cookie.getName())) {
                        String token = cookie.getValue();
                        Claims claims = jwtValidatorService.validateToken(token);
                        String username = claims.getSubject();

                        var auth = new UsernamePasswordAuthenticationToken(username, null, List.of());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                    else if ("auth_jwt".equals(cookie.getName())) {
                        String token = cookie.getValue();
                        Claims claims = jwtValidatorService.validateToken(token);
                        String username = claims.getSubject();

                        var auth = new UsernamePasswordAuthenticationToken(username, null, List.of());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            }
        } catch (JwtException e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return; // stop filter chain
        }

        filterChain.doFilter(request, response);
    }
}
