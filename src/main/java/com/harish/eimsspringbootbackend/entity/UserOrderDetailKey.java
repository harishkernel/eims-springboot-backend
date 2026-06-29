package com.harish.eimsspringbootbackend.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserOrderDetailKey implements Serializable {
    private Long orderId;
    private Long productId;
}
