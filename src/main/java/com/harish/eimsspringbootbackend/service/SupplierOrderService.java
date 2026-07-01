package com.harish.eimsspringbootbackend.service;

import com.harish.eimsspringbootbackend.dto.SupplierOrderRequestDTO;
import com.harish.eimsspringbootbackend.entity.*;
import com.harish.eimsspringbootbackend.repository.SupplierOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SupplierOrderService {
    private final SupplierOrderRepository supplierOrderRepository;
    private final ProductService productService;
    private final ProductSupplierService productSupplierService;
    private final WarehouseService warehouseService;
    private final SupplierService supplierService;
    private final InventoryService inventoryService;

    public SupplierOrderService(SupplierOrderRepository supplierOrderRepository, ProductService productService,
                                ProductSupplierService productSupplierService, WarehouseService warehouseService,
                                SupplierService supplierService, InventoryService inventoryService) {
        this.supplierOrderRepository = supplierOrderRepository;
        this.productService = productService;
        this.productSupplierService = productSupplierService;
        this.warehouseService = warehouseService;
        this.supplierService = supplierService;
        this.inventoryService = inventoryService;
    }

    public SupplierOrder getSupplierOrder(Long supplierOrderId) {
        return supplierOrderRepository.findById(supplierOrderId).orElseThrow(() -> new RuntimeException("Supplier order not found with id: " + supplierOrderId));
    }

    public List<SupplierOrder> getAllSupplierOrders() {
        return supplierOrderRepository.findAll();
    }

    public List<SupplierOrder> getOrdersByWarehouse(Long warehouseId) {
        return supplierOrderRepository.findByWarehouse_Id(warehouseId);
    }

    public List<SupplierOrder> getOrdersByProduct(Long productId) {
        return supplierOrderRepository.findByProduct_Id(productId);
    }

    public List<SupplierOrder> getOrdersByStatus(String status) {
        return supplierOrderRepository.findByStatus(status);
    }

    public List<SupplierOrder> getOrdersBySupplier(Long supplierId) {
        return supplierOrderRepository.findBySupplier_Id(supplierId);
    }

    public SupplierOrder placeSupplierOrder(SupplierOrderRequestDTO dto) {
        Supplier supplier = supplierService.getSupplier(dto.getSupplierId());
        Product product = productService.getProduct(dto.getProductId());
        ProductSupplier productSupplier = productSupplierService.getProductSupplier(dto.getProductId(), dto.getSupplierId());
        Warehouse warehouse = warehouseService.getWarehouse(dto.getWarehouseId());

        SupplierOrder supplierOrder = new SupplierOrder();
        supplierOrder.setSupplier(supplier);
        supplierOrder.setProduct(product);
        supplierOrder.setSupplierPrice(productSupplier.getSupplierPrice());
        supplierOrder.setProductQuantity(dto.getProductQuantity());
        supplierOrder.setWarehouse(warehouse);

        supplierOrder.setStatus("PENDING");
        supplierOrder.setExpectedDeliveryDate(dto.getExpectedDeliveryDate());
        supplierOrder.setSupplierOrderDate(LocalDate.now());
        supplierOrder.setReceivedDate(null);

        return supplierOrderRepository.save(supplierOrder);
    }

    public SupplierOrder updateSupplierOrder(Long supplierOrderId, SupplierOrderRequestDTO dto) {
        SupplierOrder existing = getSupplierOrder(supplierOrderId);
        existing.setProductQuantity(dto.getProductQuantity());
        existing.setExpectedDeliveryDate(dto.getExpectedDeliveryDate());
        return supplierOrderRepository.save(existing);
    }

    public SupplierOrder receiveOrder(Long supplierOrderId) {
        SupplierOrder supplierOrder = getSupplierOrder(supplierOrderId);
        supplierOrder.setStatus("RECEIVED");
        supplierOrder.setReceivedDate(LocalDate.now());
        inventoryService.increaseStock(
                supplierOrder.getWarehouse().getId(),
                supplierOrder.getProduct().getId(),
                supplierOrder.getProductQuantity(),
                supplierOrder.getReceivedDate()
        );
        return supplierOrderRepository.save(supplierOrder);
    }

    public SupplierOrder cancelSupplierOrder(Long supplierOrderId) {
        SupplierOrder supplierOrder = getSupplierOrder(supplierOrderId);
        supplierOrder.setStatus("CANCELLED");
        return supplierOrderRepository.save(supplierOrder);
    }
}
