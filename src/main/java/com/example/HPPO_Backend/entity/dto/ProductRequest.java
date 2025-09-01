package com.example.HPPO_Backend.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {
    private String name;
    private Double price;
    private String description;
    private Integer stock;
    private Long brandId;
    private Long categoryId;
    private List<String> imageUrls;
}
