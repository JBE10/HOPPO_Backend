package com.example.HPPO_Backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;
    public Category() {

    }

    public Category(String description) {
        this.description = description;
    }


}
