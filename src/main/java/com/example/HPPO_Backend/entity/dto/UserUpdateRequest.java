package com.example.HPPO_Backend.entity.dto;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String name;
    private String lastName;
    private String email;
    private String password;
}
