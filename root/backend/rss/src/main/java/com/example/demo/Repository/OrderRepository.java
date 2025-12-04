package com.example.demo.Repository;

import com.example.demo.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order , Long> {
    Optional<Order> findByUuid(String id);
}
