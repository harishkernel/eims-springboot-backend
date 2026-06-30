package com.harish.eimsspringbootbackend.repository;

import com.harish.eimsspringbootbackend.entity.ProductSupplier;
import com.harish.eimsspringbootbackend.entity.ProductSupplierKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, ProductSupplierKey> {
}
