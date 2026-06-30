package com.harish.eimsspringbootbackend.repository;

import com.harish.eimsspringbootbackend.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {

}
