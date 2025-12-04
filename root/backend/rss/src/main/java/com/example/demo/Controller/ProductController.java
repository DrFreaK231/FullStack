package com.example.demo.Controller;

import com.example.demo.Entity.Dto.ProductDto;
import com.example.demo.Entity.Product;
import com.example.demo.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
//    @GetMapping("/uuid/{uuid}")
//    public ResponseEntity<Product> getProductByUuid(@PathVariable String uuid) {
//        return ResponseEntity.ok(productService.getProductByUuid(uuid));
//    }
//
//    @PutMapping("/uuid/{uuid}")
//    public ResponseEntity<Product> updateProductByUuid(@PathVariable String uuid, @RequestBody ProductDto dto) {
//        return ResponseEntity.ok(productService.updateProductByUuid(uuid, dto));
//    }
//
//    @DeleteMapping("/uuid/{uuid}")
//    public ResponseEntity<String> deleteProductByUuid(@PathVariable String uuid) {
//        productService.deleteProductByUuid(uuid);
//        return ResponseEntity.ok("Product deleted successfully");
//    }
}
