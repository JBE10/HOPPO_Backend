package com.example.HPPO_Backend.entity.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private String role;
    private String name;
    private String lastName;
}
