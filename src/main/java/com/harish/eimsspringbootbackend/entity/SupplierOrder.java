package com.harish.eimsspringbootbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SupplierOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierOrderId;
    private Long supplierId;
    private Long warehouseId;
    private Long productId;
    private Long productQuantity;
    private BigDecimal supplierPrice;
    private String status;
    private LocalDate supplierOrderDate;
    private LocalDate expectedDeliveryDate;
    private LocalDate receivedDate;
}
