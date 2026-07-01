package com.harish.eimsspringbootbackend.service;

import com.harish.eimsspringbootbackend.dto.ProductSupplierRequestDTO;
import com.harish.eimsspringbootbackend.entity.Product;
import com.harish.eimsspringbootbackend.entity.ProductSupplier;
import com.harish.eimsspringbootbackend.entity.ProductSupplierKey;
import com.harish.eimsspringbootbackend.entity.Supplier;
import com.harish.eimsspringbootbackend.repository.ProductSupplierRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductSupplierService {
    private final ProductSupplierRepository productSupplierRepository;
    private final SupplierService supplierService;
    private final ProductService productService;

    public ProductSupplierService(ProductSupplierRepository productSupplierRepository, ProductService productService, SupplierService supplierService) {
        this.productSupplierRepository = productSupplierRepository;
        this.productService = productService;
        this.supplierService = supplierService;
    }

    public ProductSupplier getProductSupplier(Long productId, Long supplierId) {
        return productSupplierRepository.findByProduct_IdAndSupplier_Id(productId, supplierId)
                .orElseThrow(() -> new RuntimeException("Product-Supplier mapping not found"));
    }

    public List<ProductSupplier> getAllProductSuppliers() {
        return productSupplierRepository.findAll();
    }

    public ProductSupplier addProductSupplier(ProductSupplierRequestDTO dto) {
        ProductSupplier productSupplier = new ProductSupplier();
        Product product = productService.getProduct(dto.getProductId());
        Supplier supplier = supplierService.getSupplier(dto.getSupplierId());

        productSupplier.setSupplier(supplier);
        productSupplier.setProduct(product);
        productSupplier.setSupplierPrice(dto.getSupplierPrice());
        return productSupplierRepository.save(productSupplier);
    }

    public ProductSupplier updateProductSupplierPrice(ProductSupplierKey productSupplierKey, BigDecimal price) {
        ProductSupplier productSupplier = productSupplierRepository.findById(productSupplierKey)
                .orElseThrow(() -> new RuntimeException("Product-Supplier mapping not found"));
        productSupplier.setSupplierPrice(price);
        return productSupplierRepository.save(productSupplier);
    }
}
