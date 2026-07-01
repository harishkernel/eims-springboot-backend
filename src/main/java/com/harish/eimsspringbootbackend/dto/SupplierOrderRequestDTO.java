package com.harish.eimsspringbootbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierOrderRequestDTO {
    private Long supplierId;
    private Long warehouseId;
    private Long productId;
    private Integer productQuantity;
    private LocalDate expectedDeliveryDate;
}
