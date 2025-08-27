package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.User;
import com.example.HPPO_Backend.entity.dto.UserRequest;
import com.example.HPPO_Backend.exceptions.UserDuplicateException;
import com.example.HPPO_Backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public Page<User> getUsers(PageRequest pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public User createUser(UserRequest userRequest) throws UserDuplicateException {
        List<User> users = userRepository.findByUsername(userRequest.getUsername());
        if (users.isEmpty()) {
            User newUser = new User();
            newUser.setUsername(userRequest.getUsername());
            newUser.setPassword(userRequest.getPassword());
            newUser.setEmail(userRequest.getEmail());
            newUser.setRole(userRequest.getRole());
            newUser.setName(userRequest.getName());
            newUser.setLastName(userRequest.getLastName());
            return userRepository.save(newUser);
        }
        throw new UserDuplicateException();
    }
}
