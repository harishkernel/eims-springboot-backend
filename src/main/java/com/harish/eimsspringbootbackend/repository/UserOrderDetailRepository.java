package com.harish.eimsspringbootbackend.repository;

import com.harish.eimsspringbootbackend.entity.UserOrderDetail;
import com.harish.eimsspringbootbackend.entity.UserOrderDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrderDetailRepository extends JpaRepository<UserOrderDetail, UserOrderDetailKey> {

}
