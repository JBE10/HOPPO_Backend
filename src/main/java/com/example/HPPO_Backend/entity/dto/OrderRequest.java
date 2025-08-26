package com.example.HPPO_Backend.entity.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private String address;
    private String shipping;
    private Long cartId;
    private Long userId;
}
