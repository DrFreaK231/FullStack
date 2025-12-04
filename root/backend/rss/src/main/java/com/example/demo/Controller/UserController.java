package com.example.demo.Controller;

import com.example.common.dto.RegisterRequest;
import com.example.demo.Config.JwtValidatorService;
import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final JwtValidatorService jwtValidatorService;

    public UserController(UserService userService, JwtValidatorService jwtValidatorService) {
        this.userService = userService;
        this.jwtValidatorService = jwtValidatorService;
    }

    @GetMapping("/register")
    public ResponseEntity<?> registerUser(
            @CookieValue(value = "access_token", required = false) String accessToken,
            @CookieValue(value = "auth_jwt", required = false) String oauthToken
    ) {
        String token = (accessToken != null && !accessToken.isEmpty())
                ? accessToken
                : (oauthToken != null && !oauthToken.isEmpty())
                ? oauthToken
                : null;

        if (token == null) {
            return ResponseEntity
                    .status(401)
                    .body("No valid authentication cookie found");
        }

        // Extract user data from token
        RegisterRequest req = jwtValidatorService.getUserData(token);

        // Find existing user
        User existing = (req.getEmail() != null)
                ? userService.findByEmail(req.getEmail()).orElse(null)
                : userService.findByUsername(req.getUsername()).orElse(null);

        if (existing != null) {
            return ResponseEntity.ok(Map.of("uuid", existing.getUuid()));
        }

        // Create new user
        User newUser = userService.save(req);
        return ResponseEntity.ok(Map.of("uuid", newUser.getUuid()));
    }



}
