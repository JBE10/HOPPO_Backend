package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Order;
import com.example.HPPO_Backend.entity.dto.OrderRequest;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getOrders();
    Optional<Order> getOrderById(Long orderId);
    Order createOrder(OrderRequest orderRequest);
}
