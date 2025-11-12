package com.example.HPPO_Backend.repository;

import com.example.HPPO_Backend.entity.CarouselItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarouselItemRepository extends JpaRepository<CarouselItem, Long> {
    
    @Query("SELECT c FROM CarouselItem c WHERE c.isActive = true ORDER BY c.displayOrder ASC")
    List<CarouselItem> findActiveItemsOrdered();
    
    List<CarouselItem> findAllByOrderByDisplayOrderAsc();
}

