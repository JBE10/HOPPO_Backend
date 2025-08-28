package com.example.HPPO_Backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column private Integer quantity;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToOne(mappedBy = "cart")   // <- inverso de Order.cart
    private Order order;

    @OneToMany
    private List<CartProduct> items;
}
