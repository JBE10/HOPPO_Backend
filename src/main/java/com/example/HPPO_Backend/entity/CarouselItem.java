package com.example.HPPO_Backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "carousel_items")
@Data
public class CarouselItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull(message = "El producto es obligatorio")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product product;
    
    @Column(name = "display_order", nullable = false)
    @NotNull(message = "El orden de visualización es obligatorio")
    private Integer displayOrder;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "custom_title", length = 255)
    private String customTitle;
    
    @Column(name = "custom_subtitle", length = 500)
    private String customSubtitle;
}

