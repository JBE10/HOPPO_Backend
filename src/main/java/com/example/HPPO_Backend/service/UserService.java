package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.User;
import com.example.HPPO_Backend.entity.dto.UserRequest;
import com.example.HPPO_Backend.exceptions.UserDuplicateException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();
    Optional<User> getUserById(Long userId);
    User createUser(UserRequest userRequest) throws UserDuplicateException;
}
