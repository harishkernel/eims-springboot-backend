package com.harish.eimsspringbootbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentId;
    private Long orderId;
    private Long warehouseId;
    private String trackingId;
    private String carrier;
    private String status;
    private LocalDateTime dispatchDate;
    private LocalDate estimatedDeliveryDate;
    private LocalDateTime actualDeliveryDate;
}
