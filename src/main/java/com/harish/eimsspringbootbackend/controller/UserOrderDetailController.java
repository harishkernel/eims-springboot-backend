package com.harish.eimsspringbootbackend.controller;

import com.harish.eimsspringbootbackend.service.UserOrderDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class UserOrderDetailController {
    private final UserOrderDetailService userOrderDetailService;
    public UserOrderDetailController(UserOrderDetailService userOrderDetailService) {
        this.userOrderDetailService = userOrderDetailService;
    }

    @GetMapping("/{orderId}/details")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long orderId, @RequestParam Long userId) {
        return new ResponseEntity<>(userOrderDetailService.getOrderDetails(userId, orderId), HttpStatus.OK);
    }

    @GetMapping("/{orderId}/price")
    public ResponseEntity<?> getTotalPrice(@PathVariable Long orderId, @RequestParam Long userId) {
        return new ResponseEntity<>(userOrderDetailService.getTotalPrice(userId, orderId), HttpStatus.OK);
    }

    @GetMapping("/summary")
    public ResponseEntity<?> getOrderSummary(@RequestParam Long userId) {
        return new ResponseEntity<>(userOrderDetailService.getOrderSummary(userId), HttpStatus.OK);
    }
}