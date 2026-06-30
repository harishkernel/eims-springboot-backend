package com.harish.eimsspringbootbackend.repository;

import com.harish.eimsspringbootbackend.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
