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
public class UserOrderDetail {
    @EmbeddedId
    private UserOrderDetailKey userOrderDetailId;

    private Long warehouseId;
    private Integer quantity;
    private BigDecimal perQuantityPrice;
}
