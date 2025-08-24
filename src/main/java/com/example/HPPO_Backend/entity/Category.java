package com.example.HPPO_Backend.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;



}
