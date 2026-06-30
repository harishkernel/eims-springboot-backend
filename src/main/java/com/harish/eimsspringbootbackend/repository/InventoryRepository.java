package com.harish.eimsspringbootbackend.repository;

import com.harish.eimsspringbootbackend.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}
