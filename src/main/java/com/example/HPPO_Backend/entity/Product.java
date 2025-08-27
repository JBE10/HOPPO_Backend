package com.example.HPPO_Backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer stock;

    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "category_id")
    private Long categoryId;
}
