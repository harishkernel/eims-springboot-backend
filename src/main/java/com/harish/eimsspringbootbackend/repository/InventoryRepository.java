package com.harish.eimsspringbootbackend.repository;

import com.harish.eimsspringbootbackend.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByWarehouse_IdAndProduct_Id(Long warehouseId, Long productId);
}