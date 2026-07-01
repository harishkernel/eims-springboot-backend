package com.harish.eimsspringbootbackend.service;

import com.harish.eimsspringbootbackend.dto.OrderItemDTO;
import com.harish.eimsspringbootbackend.entity.Product;
import com.harish.eimsspringbootbackend.entity.UserOrder;
import com.harish.eimsspringbootbackend.entity.UserOrderDetail;
import com.harish.eimsspringbootbackend.repository.UserOrderDetailRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserOrderDetailsService {
    private final UserOrderDetailRepository userOrderDetailRepository;
    private final UserOrderService userOrderService;
    public UserOrderDetailsService(UserOrderDetailRepository userOrderDetailRepository, UserOrderService userOrderService) {
        this.userOrderDetailRepository = userOrderDetailRepository;
        this.userOrderService = userOrderService;
    }

    public List<OrderItemDTO> getOrderDetails(Long userId, Long orderId) {
        UserOrder userOrder = userOrderService.getUserOrder(orderId);
        if(!userOrder.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to order details !!");
        }
        List<OrderItemDTO> list = new ArrayList<>();
        List<UserOrderDetail> orderDetails = userOrderDetailRepository.findByOrder_OrderId(orderId);
        for(UserOrderDetail detail: orderDetails) {
            Product product = detail.getProduct();
            OrderItemDTO dto = new OrderItemDTO(
                    product.getId(),
                    product.getName(),
                    detail.getQuantity(),
                    detail.getPerQuantityPrice()
            );
            list.add(dto);
        }
        return list;
    }

    public void saveOrderDetail(UserOrderDetail detail) {
        userOrderDetailRepository.save(detail);
    }

    // explicitly created 2 methods for the ease of Payment stuff ;)
    public BigDecimal getTotalPrice(Long orderId) {
        List<UserOrderDetail> orderDetails = userOrderDetailRepository.findByOrder_OrderId(orderId);
        return orderDetails.stream()
                .map(detail -> detail.getPerQuantityPrice()
                        .multiply(BigDecimal.valueOf(detail.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalPrice(Long userId, Long orderId) {
        UserOrder userOrder = userOrderService.getUserOrder(orderId);
        if (!userOrder.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to order details!");
        }
        return getTotalPrice(orderId);
    }

    public List<UserOrderDetail> getOrderSummary(Long userId) {
        return userOrderDetailRepository.findByOrder_User_Id(userId);
    }
}
