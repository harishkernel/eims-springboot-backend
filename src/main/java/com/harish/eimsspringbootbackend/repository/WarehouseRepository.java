package com.harish.eimsspringbootbackend.repository;

import com.harish.eimsspringbootbackend.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
