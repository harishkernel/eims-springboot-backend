package com.harish.eimsspringbootbackend.controller;

import com.harish.eimsspringbootbackend.dto.UserOrderRequestDTO;
import com.harish.eimsspringbootbackend.service.UserOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class UserOrderController {
    private final UserOrderService userOrderService;
    public UserOrderController(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getUserOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(userOrderService.getUserOrder(orderId));
    }

    @GetMapping()
    public ResponseEntity<?> getOrders(@RequestParam Long userId) {
        return new ResponseEntity<>(userOrderService.getAllOrders(userId), HttpStatus.OK);
    }

    @GetMapping("/{orderId}/status")
    public ResponseEntity<?> getStatus(@PathVariable Long orderId) {
        return new ResponseEntity<>(userOrderService.getStatus(orderId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> placeOrder(@RequestBody UserOrderRequestDTO dto) {
        return new ResponseEntity<>(userOrderService.placeOrder(dto), HttpStatus.CREATED);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<?> updateStatus(@PathVariable Long orderId, @RequestParam String status) {
        return new ResponseEntity<>(userOrderService.updateStatus(orderId, status), HttpStatus.ACCEPTED);
    }

    // no delete mapping intentionally avoided, because we should only cancel order, later need for reference
}
