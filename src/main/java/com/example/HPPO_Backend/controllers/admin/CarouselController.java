package com.example.HPPO_Backend.controllers.admin;

import com.example.HPPO_Backend.entity.CarouselItem;
import com.example.HPPO_Backend.entity.dto.CarouselItemRequest;
import com.example.HPPO_Backend.entity.dto.CarouselItemResponse;
import com.example.HPPO_Backend.service.CarouselService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/carousel")
public class CarouselController {
    
    @Autowired
    private CarouselService carouselService;
    
    @GetMapping
    public ResponseEntity<List<CarouselItemResponse>> getAllCarouselItems() {
        List<CarouselItem> items = carouselService.getAllCarouselItems();
        List<CarouselItemResponse> responses = items.stream()
                .map(CarouselItemResponse::fromCarouselItem)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CarouselItemResponse> getCarouselItemById(@PathVariable Long id) {
        CarouselItem item = carouselService.getCarouselItemById(id);
        return ResponseEntity.ok(CarouselItemResponse.fromCarouselItem(item));
    }
    
    @PostMapping
    public ResponseEntity<CarouselItemResponse> createCarouselItem(@Valid @RequestBody CarouselItemRequest request) {
        CarouselItem item = carouselService.createCarouselItem(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CarouselItemResponse.fromCarouselItem(item));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CarouselItemResponse> updateCarouselItem(
            @PathVariable Long id,
            @Valid @RequestBody CarouselItemRequest request) {
        CarouselItem item = carouselService.updateCarouselItem(id, request);
        return ResponseEntity.ok(CarouselItemResponse.fromCarouselItem(item));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarouselItem(@PathVariable Long id) {
        carouselService.deleteCarouselItem(id);
        return ResponseEntity.noContent().build();
    }
}

