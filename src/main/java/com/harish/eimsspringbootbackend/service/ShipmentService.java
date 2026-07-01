package com.harish.eimsspringbootbackend.service;

import com.harish.eimsspringbootbackend.dto.ShipmentRequestDTO;
import com.harish.eimsspringbootbackend.entity.Payment;
import com.harish.eimsspringbootbackend.entity.Shipment;
import com.harish.eimsspringbootbackend.entity.UserOrder;
import com.harish.eimsspringbootbackend.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final UserOrderService userOrderService;
    private final PaymentService paymentService;
    private final WarehouseService warehouseService;

    public ShipmentService(ShipmentRepository shipmentRepository, UserOrderService userOrderService, PaymentService paymentService, WarehouseService warehouseService) {
        this.shipmentRepository = shipmentRepository;
        this.userOrderService = userOrderService;
        this.paymentService = paymentService;
        this.warehouseService = warehouseService;
    }

    public Shipment getShipment(Long shipmentId) {
        return shipmentRepository.findById(shipmentId).orElseThrow(() -> new RuntimeException("Shipment not found with id: " + shipmentId));
    }

    public Shipment getShipmentByOrder(Long orderId) {
        return shipmentRepository.findByUserOrder_OrderId(orderId).orElseThrow(() -> new RuntimeException("Shipment not found"));
    }

    public List<Shipment> getShipmentsByWarehouse(Long warehouseId) {
        return shipmentRepository.findByWarehouse_Id(warehouseId);
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public List<Shipment> getShipmentsByStatus(String status) {
        return shipmentRepository.findByStatus(status);
    }

    private String generateTrackingId(LocalDateTime date, String carrier, String userName) {
        // Example: FED-260701-HM-A4B9 ---> Carrier: FedEx, User: Harish Muthaiyan, Date: July 1, 2026
        // 1. Carrier Code (Up to the first 3 letters, Uppercase)
        String cleanCarrier = carrier.trim();
        String carrierCode = cleanCarrier.substring(0, Math.min(3, cleanCarrier.length())).toUpperCase();

        // 2. Date Code (Format: YYMMDD)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String dateCode = date.format(formatter);

        // 3. User Initials (First + Last, or up to first 2 letters if single name)
        String[] nameParts = userName.trim().split("\\s+");
        String initials = nameParts.length >= 2
                ? (nameParts[0].charAt(0) + nameParts[nameParts.length - 1].substring(0, 1)).toUpperCase()
                : nameParts[0].substring(0, Math.min(2, nameParts[0].length())).toUpperCase();

        // 4. First 4 characters of a UUID
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return String.format("%s-%s-%s-%s", carrierCode, dateCode, initials, uniqueSuffix);
    }

    public Shipment createShipment(ShipmentRequestDTO dto) {
        UserOrder userOrder = userOrderService.getUserOrder(dto.getOrderId());
        Payment payment = paymentService.getPaymentByOrder(dto.getOrderId());

        if (!payment.getStatus().equals("SUCCESS")) {
            throw new RuntimeException("Payment is not completed!!");
        }

        String trackingId = generateTrackingId(LocalDateTime.now(), dto.getCarrier(), userOrder.getUser().getName());
        Shipment shipment = new Shipment();
        shipment.setUserOrder(userOrder);
        shipment.setCarrier(dto.getCarrier());
        shipment.setWarehouse(warehouseService.getWarehouse(dto.getWarehouseId()));
        shipment.setTrackingId(trackingId);
        shipment.setStatus("CREATED");
        return shipmentRepository.save(shipment);
    }

    public Shipment dispatchShipment(Long shipmentId) {
        Shipment shipment = getShipment(shipmentId);
        UserOrder order = shipment.getUserOrder();

        // if status == delivered or smth else then throw ERROR
        if (!shipment.getStatus().equals("CREATED")) {
            throw new RuntimeException("Shipment cannot be dispatched from current status: " + shipment.getStatus());
        }

        shipment.setStatus("DISPATCHED");
        shipment.setDispatchDate(LocalDateTime.now());
        shipment.setEstimatedDeliveryDate(shipment.getDispatchDate().toLocalDate().plusDays(7));
        return shipmentRepository.save(shipment);
    }

    public Shipment deliverShipment(Long shipmentId) {
        Shipment shipment = getShipment(shipmentId);
        if (!shipment.getStatus().equals("DISPATCHED")) {
            throw new RuntimeException("Shipment cannot be delivered from current status: " + shipment.getStatus());
        }
        shipment.setStatus("DELIVERED");
        shipment.setActualDeliveryDate(LocalDateTime.now());
        return shipmentRepository.save(shipment);
    }

    public Shipment cancelShipment(Long shipmentId) {
        Shipment shipment = getShipment(shipmentId);
        if (shipment.getStatus().equals("DELIVERED")) {
            throw new RuntimeException(
                    "Delivered shipment cannot be cancelled.");
        }
        shipment.setStatus("CANCELLED");
        return shipmentRepository.save(shipment);
    }

    public Shipment updateEstimatedDeliveryDate(Long shipmentId, LocalDate estimatedDeliveryDate) {
        Shipment shipment = getShipment(shipmentId);
        if(shipment.getStatus().equals("DELIVERED")) throw new RuntimeException("Shipment was delivered already");
        if(shipment.getStatus().equals("CANCELLED")) throw new RuntimeException("Shipment was cancelled already");
        shipment.setEstimatedDeliveryDate(estimatedDeliveryDate);
        return shipmentRepository.save(shipment);
    }
}
