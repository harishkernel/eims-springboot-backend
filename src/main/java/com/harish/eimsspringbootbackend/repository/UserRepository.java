package com.harish.eimsspringbootbackend.repository;

import com.harish.eimsspringbootbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
