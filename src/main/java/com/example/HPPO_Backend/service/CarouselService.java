package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.CarouselItem;
import com.example.HPPO_Backend.entity.dto.CarouselItemRequest;

import java.util.List;

public interface CarouselService {
    List<CarouselItem> getActiveCarouselItems();
    List<CarouselItem> getAllCarouselItems();
    CarouselItem createCarouselItem(CarouselItemRequest request);
    CarouselItem updateCarouselItem(Long id, CarouselItemRequest request);
    void deleteCarouselItem(Long id);
    CarouselItem getCarouselItemById(Long id);
}

