package com.harish.eimsspringbootbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequestDTO {
    private Long productId;
    private Long warehouseId;
    private Integer quantity;
    private LocalDate lastUpdated;
}
