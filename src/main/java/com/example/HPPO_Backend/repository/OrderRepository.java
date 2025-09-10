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
    // CAMBIAR de buscar una sola orden por carrito a múltiples
    List<Order> findByCartId(Long cartId);

    // Método útil para encontrar órdenes por usuario
    List<Order> findByUserId(Long userId);

    // Método para encontrar la última orden de un usuario
    Optional<Order> findTopByUserIdOrderByOrderDateDesc(Long userId);


    // Nuevo método para obtener órdenes de un usuario con paginación y ordenadas por fecha descendente
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId ORDER BY o.orderDate DESC")
    Page<Order> findByUserIdOrderByOrderDateDesc(@Param("userId") Long userId, Pageable pageable);
}