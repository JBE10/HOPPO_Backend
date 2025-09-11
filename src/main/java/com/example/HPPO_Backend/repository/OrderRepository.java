package com.example.HPPO_Backend.repository;

import com.example.HPPO_Backend.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {



    List<Order> findByUserId(Long userId);


    Optional<Order> findTopByUserIdOrderByOrderDateDesc(Long userId);



    @Query(value = "SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.items WHERE o.user.id = :userId",
            countQuery = "SELECT COUNT(o) FROM Order o WHERE o.user.id = :userId")
    Page<Order> findByUserIdOrderByOrderDateDesc(@Param("userId") Long userId, Pageable pageable);

}