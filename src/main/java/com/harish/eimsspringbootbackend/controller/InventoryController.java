package com.harish.eimsspringbootbackend.controller;

import com.harish.eimsspringbootbackend.dto.InventoryRequestDTO;
import com.harish.eimsspringbootbackend.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventories")
public class InventoryController {
    private final InventoryService inventoryService;
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllInventories() {
        return new ResponseEntity<>(inventoryService.getAllInventories(), HttpStatus.OK);
    }

    @GetMapping("/{inventoryId}")
    public ResponseEntity<?> getInventory(@PathVariable Long inventoryId) {
        return new ResponseEntity<>(inventoryService.getInventory(inventoryId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getInventoryByWarehouseAndProduct(@RequestParam Long warehouseId, @RequestParam Long productId) {
        return new ResponseEntity<>(inventoryService.getInventoryByWarehouseAndProduct(warehouseId, productId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addInventory(@RequestBody InventoryRequestDTO dto) {
        return new ResponseEntity<>(inventoryService.addInventory(dto), HttpStatus.CREATED);
    }
}
