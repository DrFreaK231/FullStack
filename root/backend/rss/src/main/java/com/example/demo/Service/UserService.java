package com.example.demo.Service;

import com.example.common.dto.RegisterRequest;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(RegisterRequest request) {
        return repo.findByEmail(request.getEmail())
                .orElseGet(() -> {
                    User user = new User();
                    user.setUsername(request.getUsername());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setEmail(request.getEmail());
                    user.setPhoneNumber(request.getPhoneNumber());
                    return repo.save(user);
                });
    }

    public User findByUuid(String uuid) {
        return repo.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("User not found with UUID: " + uuid));
    }

    public Optional<User> findByEmail(String email){
        return repo.findByEmail(email);
    }
    public Optional<User> findByUsername(String username){
        return repo.findByUsername(username);
    }
}
