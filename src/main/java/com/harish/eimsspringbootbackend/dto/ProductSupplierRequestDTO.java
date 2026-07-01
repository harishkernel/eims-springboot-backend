package com.harish.eimsspringbootbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSupplierRequestDTO {
    private Long productId;
    private Long supplierId;
    private BigDecimal supplierPrice;
    private Integer supplierRating;
}
