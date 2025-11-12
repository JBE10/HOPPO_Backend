package com.example.HPPO_Backend.entity.dto;

import com.example.HPPO_Backend.entity.CarouselItem;
import lombok.Data;

@Data
public class CarouselItemResponse {
    private Long id;
    private ProductResponse product;
    private Integer displayOrder;
    private Boolean isActive;
    private String customTitle;
    private String customSubtitle;
    
    public static CarouselItemResponse fromCarouselItem(CarouselItem item) {
        CarouselItemResponse response = new CarouselItemResponse();
        response.setId(item.getId());
        response.setProduct(ProductResponse.fromProduct(item.getProduct()));
        response.setDisplayOrder(item.getDisplayOrder());
        response.setIsActive(item.getIsActive());
        response.setCustomTitle(item.getCustomTitle());
        response.setCustomSubtitle(item.getCustomSubtitle());
        return response;
    }
}

