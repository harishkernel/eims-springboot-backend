package com.harish.eimsspringbootbackend.repository;

import com.harish.eimsspringbootbackend.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByWarehouse_Id(Long warehouseId);
    List<Shipment> findByStatus(String status);
    Optional<Shipment> findByUserOrder_OrderId(Long orderId);
}
