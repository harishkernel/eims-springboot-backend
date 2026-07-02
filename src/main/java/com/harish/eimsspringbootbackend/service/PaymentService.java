package com.harish.eimsspringbootbackend.service;

import com.harish.eimsspringbootbackend.dto.PaymentRequestDTO;
import com.harish.eimsspringbootbackend.entity.Payment;
import com.harish.eimsspringbootbackend.entity.UserOrder;
import com.harish.eimsspringbootbackend.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserOrderDetailService userOrderDetailService;
    private final UserOrderService userOrderService;

    public PaymentService(PaymentRepository paymentRepository, UserOrderDetailService userOrderDetailService,
                          UserOrderService userOrderService) {
        this.paymentRepository = paymentRepository;
        this.userOrderDetailService = userOrderDetailService;
        this.userOrderService = userOrderService;
    }

    public Payment getPayment(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));
    }

    public Payment getPaymentByOrder(Long orderId) {
        return paymentRepository.findByUserOrder_OrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found for order id: " + orderId));
    }

    public List<Payment> getPaymentsByUser(Long userId) {
        return paymentRepository.findByUserOrder_User_Id(userId);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment initiatePayment(PaymentRequestDTO dto) {
        if (paymentRepository.findByUserOrder_OrderId(dto.getOrderId()).isPresent()) {
            throw new RuntimeException("Payment already exists for order id: " + dto.getOrderId());
        }
        UserOrder order = userOrderService.getUserOrder(dto.getOrderId());
        Payment payment = new Payment();
        payment.setUserOrder(order);
        payment.setAmount(
                userOrderDetailService.getTotalPrice(order.getOrderId()));
        payment.setPaymentMode(dto.getPaymentMode());
        payment.setTxnId(UUID.randomUUID().toString());
        payment.setStatus("SUCCESS");
        payment.setPaymentDate(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    public Payment updatePaymentStatus(Long paymentId, String status) {
        Payment payment = getPayment(paymentId);
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }
}