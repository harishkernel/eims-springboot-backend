package com.harish.eimsspringbootbackend.repository;

import com.harish.eimsspringbootbackend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserOrder_User_Id(Long userId);
    Optional<Payment> findByUserOrder_OrderId(Long orderId);
}
