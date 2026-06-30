package com.harish.eimsspringbootbackend.service;

import com.harish.eimsspringbootbackend.dto.InventoryRequestDTO;
import com.harish.eimsspringbootbackend.entity.Inventory;
import com.harish.eimsspringbootbackend.entity.Product;
import com.harish.eimsspringbootbackend.entity.Warehouse;
import com.harish.eimsspringbootbackend.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductService productService;
    private final WarehouseService warehouseService;

    public InventoryService(InventoryRepository inventoryRepository, ProductService productService,
                            WarehouseService warehouseService) {
        this.inventoryRepository = inventoryRepository;
        this.productService = productService;
        this.warehouseService = warehouseService;
    }

    public Inventory getInventory(Long inventoryId) {
        return inventoryRepository.findById(inventoryId).orElseThrow(() -> new RuntimeException("Inventory not found with id: " + inventoryId));
    }

    public Optional<Inventory> findInventory(Long warehouseId, Long productId) {
        return inventoryRepository.findByWarehouse_IdAndProduct_Id(warehouseId, productId);
    }

    public Inventory getInventoryByWarehouseAndProduct(Long warehouseId, Long productId) {
        return findInventory(warehouseId, productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found with product id: " + productId + " and warehouse id: " + warehouseId));
    }

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public Inventory addInventory(InventoryRequestDTO dto) {
        Product product = productService.getProduct(dto.getProductId());
        Warehouse warehouse = warehouseService.getWarehouse(dto.getWarehouseId());
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setWarehouse(warehouse);
        inventory.setQuantity(dto.getQuantity());
        inventory.setLastUpdated(dto.getLastUpdated());

        return inventoryRepository.save(inventory);
    }

    public boolean isLowStock(Long warehouseId, Long productId) {
        Inventory inventory = getInventoryByWarehouseAndProduct(warehouseId, productId);
        return inventory.getQuantity() <
                productService.getProduct(productId).getThreshold();
    }

    public void increaseStock(Long warehouseId, Long productId, Integer quantity, LocalDate updatedDate) {
        Optional<Inventory> existing = findInventory(warehouseId, productId);

        if (existing.isPresent()) {
            Inventory inventory = existing.get();
            inventory.setQuantity(inventory.getQuantity() + quantity);
            inventory.setLastUpdated(updatedDate);
            inventoryRepository.save(inventory);
        } else {
            InventoryRequestDTO dto = new InventoryRequestDTO(
                    productId, warehouseId, quantity, updatedDate
            );
            addInventory(dto);
        }
    }

    public void decreaseStock(Long warehouseId, Long productId, Integer quantity, LocalDate updatedDate) {
        Inventory inventory = getInventoryByWarehouseAndProduct(warehouseId, productId);
        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }
        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventory.setLastUpdated(updatedDate);
        inventoryRepository.save(inventory);
    }
}