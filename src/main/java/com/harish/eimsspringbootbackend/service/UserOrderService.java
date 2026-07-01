package com.harish.eimsspringbootbackend.service;

import com.harish.eimsspringbootbackend.dto.OrderItemDTO;
import com.harish.eimsspringbootbackend.dto.UserOrderRequestDTO;
import com.harish.eimsspringbootbackend.entity.*;
import com.harish.eimsspringbootbackend.repository.UserOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserOrderService {
    private final UserOrderRepository userOrderRepository;
    private final InventoryService inventoryService;
    private final UserService userService;
    private final ProductService productService;
    private final WarehouseService warehouseService;
    private final UserOrderDetailsService userOrderDetailsService;

    public UserOrderService(UserOrderRepository userOrderRepository, InventoryService inventoryService, UserService userService, ProductService productService, WarehouseService warehouseService, UserOrderDetailsService userOrderDetailsService) {
        this.userOrderRepository = userOrderRepository;
        this.userService = userService;
        this.inventoryService = inventoryService;
        this.productService = productService;
        this.warehouseService = warehouseService;
        this.userOrderDetailsService = userOrderDetailsService;
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

    @Transactional
    public UserOrder placeOrder(UserOrderRequestDTO dto) {
        // check item stock first, if any 1 less then dont place entire order
        // suggest user to remove that item from cart
        // create order, deduct items and saveDetails
        User user = userService.getUser(dto.getUserId());
        Warehouse warehouse = warehouseService.getWarehouse(dto.getWarehouseId());
        Long warehouseId = warehouse.getId();

        List<OrderItemDTO> cartItems = dto.getItems();
        for(OrderItemDTO item: cartItems) {
            if(inventoryService.isLowStock(warehouseId, item.getProductId())) {
                throw new RuntimeException(item.getProductName() + " is out of stock, kindly remove it from cart!!");
            }
        }

        UserOrder userOrder = new UserOrder();
        userOrder.setUser(user);
        userOrder.setOrderDate(LocalDate.now());
        userOrder.setStatus("PENDING");
        UserOrder savedOrder = userOrderRepository.save(userOrder);

        LocalDate updatedDate = savedOrder.getOrderDate();

        for(OrderItemDTO item: cartItems) {
            Integer quantity = item.getQuantity();
            Long productId = item.getProductId();
            inventoryService.decreaseStock(warehouseId, productId, quantity, updatedDate);

            Product product = productService.getProduct(productId);

            UserOrderDetail userOrderDetail = new UserOrderDetail();
            userOrderDetail.setUserOrderDetailId(new UserOrderDetailKey(savedOrder.getOrderId(), productId));

            userOrderDetail.setProduct(product);
            userOrderDetail.setQuantity(quantity);
            userOrderDetail.setOrder(savedOrder);
            userOrderDetail.setWarehouse(warehouse);
            userOrderDetail.setPerQuantityPrice(product.getPrice());

            userOrderDetailsService.saveOrderDetail(userOrderDetail);
        }
        return savedOrder;
    }

    public UserOrder saveOrder(UserOrder userOrder) {
        return userOrderRepository.save(userOrder);
    }

    public UserOrder updateStatus(Long orderId, String status) {
        UserOrder userOrder = getUserOrder(orderId);
        userOrder.setStatus(status);
        if(status.equals("DELIVERED")) userOrder.setDeliveryDate(LocalDate.now());
        return userOrderRepository.save(userOrder);
    }
}
