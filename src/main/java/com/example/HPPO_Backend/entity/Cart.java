package com.example.HPPO_Backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer quantity;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private User user;

    @OneToOne
    @JoinColumn(name = "purchase_order_id")
    private Order purchaseOrder;

    @OneToMany
    private List<CartProduct> items;
}
