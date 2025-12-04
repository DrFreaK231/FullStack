package com.example.demo.Service;

import com.example.demo.Entity.Category;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.Dto.ProductDto;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Product createProduct(ProductDto dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Transactional
    public Product updateProductByUuid(String uuid, ProductDto dto) {

        Product product = productRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());

        product.setCategory(category);

        return productRepository.save(product);
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product getProductByUuid(String uuid) {
        return productRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProductByUuid(String uuid) {
        if (!productRepository.existsByUuid(uuid)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteByUuid(uuid);
    }
}
