package com.harish.eimsspringbootbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentRequestDTO {
    private Long orderId;
    private Long warehouseId;
    private String carrier;
    private LocalDate estimatedDeliveryDate;
}
