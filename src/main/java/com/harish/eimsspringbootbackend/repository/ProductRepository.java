package com.harish.eimsspringbootbackend.repository;

import com.harish.eimsspringbootbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
}
