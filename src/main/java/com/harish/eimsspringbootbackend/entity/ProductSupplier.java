package com.harish.eimsspringbootbackend.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductSupplier {
    @EmbeddedId
    private ProductSupplierKey productSupplierId;
    private BigDecimal supplierPrice;
    private Integer supplierRating;
}
