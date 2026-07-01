package com.harish.eimsspringbootbackend.service;

import com.harish.eimsspringbootbackend.dto.WarehouseRequestDTO;
import com.harish.eimsspringbootbackend.entity.Warehouse;
import com.harish.eimsspringbootbackend.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse getWarehouse(Long warehouseId) {
        return warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + warehouseId));
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse addWarehouse(WarehouseRequestDTO dto) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(dto.getName());
        warehouse.setState(dto.getState());
        warehouse.setCity(dto.getCity());
        warehouse.setAddress(dto.getAddress());
        warehouse.setPhone(dto.getPhone());
        warehouse.setEmail(dto.getEmail());
        return warehouseRepository.save(warehouse);
    }

    public Warehouse updateWarehouse(Long warehouseId, WarehouseRequestDTO dto) {
        Warehouse existing = getWarehouse(warehouseId);
        existing.setAddress(dto.getAddress());
        existing.setEmail(dto.getEmail());
        existing.setName(dto.getName());
        existing.setCity(dto.getCity());
        existing.setPhone(dto.getPhone());
        existing.setState(dto.getState());
        return warehouseRepository.save(existing);
    }

    public void deleteWarehouse(Long warehouseId) {
        warehouseRepository.deleteById(warehouseId);
    }
}
