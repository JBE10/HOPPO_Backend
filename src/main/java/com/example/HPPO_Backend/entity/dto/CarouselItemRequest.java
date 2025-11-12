package com.example.HPPO_Backend.entity.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarouselItemRequest {
    
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;
    
    @NotNull(message = "El orden de visualización es obligatorio")
    private Integer displayOrder;
    
    private Boolean isActive = true;
    
    private String customTitle;
    
    private String customSubtitle;
}

