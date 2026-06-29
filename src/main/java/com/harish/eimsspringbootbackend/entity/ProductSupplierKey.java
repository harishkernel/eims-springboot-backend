package com.harish.eimsspringbootbackend.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProductSupplierKey implements Serializable {
    private Long productId;
    private Long supplierId;
}
