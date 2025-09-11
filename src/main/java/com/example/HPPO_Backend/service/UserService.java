package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.User;
import com.example.HPPO_Backend.entity.dto.UserRequest;
import com.example.HPPO_Backend.exceptions.UserDuplicateException;

import java.util.Optional;
import com.example.HPPO_Backend.entity.dto.UserUpdateRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {
    Page<User> getUsers(PageRequest pageRequest);
    User getUserById(Long userId);
    User updateSelf(Long currentUserId, UserUpdateRequest request);
    void deleteUser(Long userId);
}