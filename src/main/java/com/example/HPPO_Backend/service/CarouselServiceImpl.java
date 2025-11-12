package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.CarouselItem;
import com.example.HPPO_Backend.entity.Product;
import com.example.HPPO_Backend.entity.dto.CarouselItemRequest;
import com.example.HPPO_Backend.repository.CarouselItemRepository;
import com.example.HPPO_Backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {
    
    @Autowired
    private CarouselItemRepository carouselItemRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public List<CarouselItem> getActiveCarouselItems() {
        return carouselItemRepository.findActiveItemsOrdered();
    }
    
    @Override
    public List<CarouselItem> getAllCarouselItems() {
        return carouselItemRepository.findAllByOrderByDisplayOrderAsc();
    }
    
    @Override
    @Transactional
    public CarouselItem createCarouselItem(CarouselItemRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                        "Producto no encontrado con id: " + request.getProductId()));
        
        // Verificar que el producto tenga imágenes
        if (product.getImages() == null || product.getImages().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                    "El producto debe tener al menos una imagen para aparecer en el carrusel");
        }
        
        CarouselItem item = new CarouselItem();
        item.setProduct(product);
        item.setDisplayOrder(request.getDisplayOrder());
        item.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        item.setCustomTitle(request.getCustomTitle());
        item.setCustomSubtitle(request.getCustomSubtitle());
        
        return carouselItemRepository.save(item);
    }
    
    @Override
    @Transactional
    public CarouselItem updateCarouselItem(Long id, CarouselItemRequest request) {
        CarouselItem item = carouselItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                        "Item del carrusel no encontrado con id: " + id));
        
        if (request.getProductId() != null) {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                            "Producto no encontrado con id: " + request.getProductId()));
            
            // Verificar que el producto tenga imágenes
            if (product.getImages() == null || product.getImages().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                        "El producto debe tener al menos una imagen para aparecer en el carrusel");
            }
            
            item.setProduct(product);
        }
        
        if (request.getDisplayOrder() != null) {
            item.setDisplayOrder(request.getDisplayOrder());
        }
        
        if (request.getIsActive() != null) {
            item.setIsActive(request.getIsActive());
        }
        
        if (request.getCustomTitle() != null) {
            item.setCustomTitle(request.getCustomTitle());
        }
        
        if (request.getCustomSubtitle() != null) {
            item.setCustomSubtitle(request.getCustomSubtitle());
        }
        
        return carouselItemRepository.save(item);
    }
    
    @Override
    @Transactional
    public void deleteCarouselItem(Long id) {
        if (!carouselItemRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                    "Item del carrusel no encontrado con id: " + id);
        }
        carouselItemRepository.deleteById(id);
    }
    
    @Override
    public CarouselItem getCarouselItemById(Long id) {
        return carouselItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                        "Item del carrusel no encontrado con id: " + id));
    }
}

