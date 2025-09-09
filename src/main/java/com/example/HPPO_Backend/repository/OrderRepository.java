package com.example.HPPO_Backend.repository;

import com.example.HPPO_Backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // CAMBIAR de buscar una sola orden por carrito a múltiples
    List<Order> findByCartId(Long cartId);

    // Método útil para encontrar órdenes por usuario
    List<Order> findByUserId(Long userId);

    // Método para encontrar la última orden de un usuario
    Optional<Order> findTopByUserIdOrderByOrderDateDesc(Long userId);
}