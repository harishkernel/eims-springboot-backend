package com.harish.eimsspringbootbackend.service;

import com.harish.eimsspringbootbackend.entity.UserOrder;
import com.harish.eimsspringbootbackend.repository.UserOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserOrderService {
    private final UserOrderRepository userOrderRepository;
    public UserOrderService(UserOrderRepository userOrderRepository) {
        this.userOrderRepository = userOrderRepository;
    }

    public UserOrder getUserOrder(Long orderId) {
        return userOrderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    public List<UserOrder> getAllOrders(Long userId) {
        return userOrderRepository.findByUser_Id(userId);
    }

    public String getStatus(Long orderId) {
        return getUserOrder(orderId).getStatus();
    }

    public UserOrder updateStatus(Long orderId, String status) {
        UserOrder userOrder = getUserOrder(orderId);
        userOrder.setStatus(status);
        if(status.equals("DELIVERED")) userOrder.setDeliveryDate(LocalDate.now());
        return userOrderRepository.save(userOrder);
    }
}
