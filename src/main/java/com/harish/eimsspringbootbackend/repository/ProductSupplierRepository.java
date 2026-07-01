package com.harish.eimsspringbootbackend.repository;

import com.harish.eimsspringbootbackend.entity.ProductSupplier;
import com.harish.eimsspringbootbackend.entity.ProductSupplierKey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, ProductSupplierKey> {
    Optional<ProductSupplier> findByProduct_IdAndSupplier_Id(Long productId, Long supplierId);
}
