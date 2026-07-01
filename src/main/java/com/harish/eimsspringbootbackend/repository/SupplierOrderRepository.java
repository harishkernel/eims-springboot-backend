package com.harish.eimsspringbootbackend.repository;

import com.harish.eimsspringbootbackend.entity.SupplierOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierOrderRepository extends JpaRepository<SupplierOrder, Long> {
    List<SupplierOrder> findByWarehouse_Id(Long warehouseId);
    List<SupplierOrder> findByProduct_Id(Long productId);
    List<SupplierOrder> findByStatus(String status);
    List<SupplierOrder> findBySupplier_Id(Long supplierId);
}
