package com.example.HPPO_Backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer quantity;


    @ManyToOne
    @JoinColumn(name = "id_cart")
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

}
