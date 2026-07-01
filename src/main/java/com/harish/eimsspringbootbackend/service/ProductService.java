package com.harish.eimsspringbootbackend.service;

import com.harish.eimsspringbootbackend.dto.ProductRequestDTO;
import com.harish.eimsspringbootbackend.entity.Product;
import com.harish.eimsspringbootbackend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(ProductRequestDTO dto) {
        if (productRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Product already exists");
        }
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());
        product.setThreshold(dto.getThreshold());
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, ProductRequestDTO dto) {
        Product existing = getProduct(productId);
        existing.setName(dto.getName());
        existing.setPrice(dto.getPrice());
        existing.setImageUrl(dto.getImageUrl());
        existing.setDescription(dto.getDescription());
        return productRepository.save(existing);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
