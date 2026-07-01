package com.harish.eimsspringbootbackend.service;

import com.harish.eimsspringbootbackend.dto.UserRequestDTO;
import com.harish.eimsspringbootbackend.entity.User;
import com.harish.eimsspringbootbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User registerUser(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setState(dto.getState());
        user.setCountry(dto.getCountry());
        return userRepository.save(user);
    }

    public User updateUser(Long userId, UserRequestDTO dto) {
        User existing = getUser(userId);
        if(!existing.getRole().equals(dto.getRole())) {
            throw new RuntimeException("Unauthorized access to change role !!");
        }
        existing.setAddress(dto.getAddress());
        existing.setPhone(dto.getPhone());
        existing.setState(dto.getState());
        existing.setCountry(dto.getCountry());
        existing.setEmail(dto.getEmail());
        existing.setPassword(dto.getPassword());
        return userRepository.save(existing);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
