package com.example.demo.Repository;

import com.example.demo.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByUuid(String uuid);
    boolean existsByUuid(String uuid);
    void deleteByUuid(String uuid);
}
