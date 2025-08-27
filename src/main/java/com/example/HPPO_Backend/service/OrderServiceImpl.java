package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Order;
import com.example.HPPO_Backend.entity.User;
import com.example.HPPO_Backend.entity.dto.OrderRequest;
import com.example.HPPO_Backend.repository.OrderRepository;
import com.example.HPPO_Backend.repository.UserRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public Page<Order> getOrders(PageRequest pageable) {
        return orderRepository.findAll(pageable);
    }

    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Transactional
    public Order createOrder(OrderRequest orderRequest) {

        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: id=" + orderRequest.getUserId()));


        Order newOrder = new Order();
        newOrder.setAddress(orderRequest.getAddress());
        newOrder.setShipping(orderRequest.getShipping());
        newOrder.setCartId(orderRequest.getCartId());
        newOrder.setUser(user);


         newOrder.setTotal(orderRequest.getTotal());
         newOrder.setOrderDate(LocalDateTime.now());


        return orderRepository.save(newOrder);
    }
}
