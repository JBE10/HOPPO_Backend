package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.User;
import com.example.HPPO_Backend.entity.dto.UserRequest;
import com.example.HPPO_Backend.exceptions.UserDuplicateException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface UserService {
    Page<User> getUsers(PageRequest pageRequest);
    Optional<User> getUserById(Long userId);
    User createUser(UserRequest userRequest) throws UserDuplicateException;
}
