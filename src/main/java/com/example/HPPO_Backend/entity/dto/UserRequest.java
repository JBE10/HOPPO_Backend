package com.example.HPPO_Backend.entity.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String name;
    private String lastName;
    private Long roleId;
}
