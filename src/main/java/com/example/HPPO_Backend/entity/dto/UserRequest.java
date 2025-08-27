package com.example.HPPO_Backend.entity.dto;

import lombok.Data;
import com.example.HPPO_Backend.entity.Role;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String name;
    private String lastName;
    private Role role;
}
