package com.harish.eimsspringbootbackend.repository;

import com.harish.eimsspringbootbackend.entity.UserOrderDetail;
import com.harish.eimsspringbootbackend.entity.UserOrderDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserOrderDetailRepository extends JpaRepository<UserOrderDetail, UserOrderDetailKey> {

    List<UserOrderDetail> findByOrder_OrderId(Long orderId);

    List<UserOrderDetail> findByOrder_User_Id(Long orderUserId);
}
