package com.example.HPPO_Backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String address;
    @Column(nullable = false) private String shipping;
    @Column private Double total;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @OneToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = OrderStatus.CREATED;
        }

    }
}
