package com.example.HPPO_Backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String shipping;

    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "user_id")
    private Long userId;
}
