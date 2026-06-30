package com.harish.eimsspringbootbackend.repository;

import com.harish.eimsspringbootbackend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
