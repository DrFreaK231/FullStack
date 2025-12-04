package com.example.demo.Repository;

import com.example.demo.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrderSessionId(String orderSessionId);
}

