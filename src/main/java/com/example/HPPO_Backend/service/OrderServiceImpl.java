package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Order;
import com.example.HPPO_Backend.entity.dto.OrderRequest;
import com.example.HPPO_Backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Page<Order> getOrders(PageRequest pageRequest) {
        return orderRepository.findAll(pageRequest);
    }

    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public Order createOrder(OrderRequest orderRequest) {
        Order newOrder = new Order();
        newOrder.setAddress(orderRequest.getAddress());
        newOrder.setShipping(orderRequest.getShipping());
        newOrder.setCartId(orderRequest.getCartId());
        newOrder.setUserId(orderRequest.getUserId());
        return orderRepository.save(newOrder);
    }
}
