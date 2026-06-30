package com.harish.eimsspringbootbackend.repository;

import com.harish.eimsspringbootbackend.entity.SupplierOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierOrderRepository extends JpaRepository<SupplierOrder, Long> {
}
