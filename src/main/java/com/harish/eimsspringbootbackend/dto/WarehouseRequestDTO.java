package com.harish.eimsspringbootbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseRequestDTO {
    private String name;
    private String address;
    private String email;
    private String city;
    private String state;
    private String phone;
}
