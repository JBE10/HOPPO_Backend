package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Cart;
import com.example.HPPO_Backend.entity.Order;
import com.example.HPPO_Backend.entity.User;
import com.example.HPPO_Backend.entity.dto.OrderRequest;
import com.example.HPPO_Backend.repository.CartRepository;
import com.example.HPPO_Backend.repository.OrderRepository;
import com.example.HPPO_Backend.repository.UserRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado: id=" + orderRequest.getUserId()));

        Cart cart = cartRepository.findById(orderRequest.getCartId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado: id=" + orderRequest.getCartId()));

        if(orderRepository.findByCartId(orderRequest.getCartId()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El carrito ya se encuentra en una orden.");
        }

        Order newOrder = new Order();
        newOrder.setAddress(orderRequest.getAddress());
        newOrder.setShipping(orderRequest.getShipping());
        newOrder.setTotal(orderRequest.getTotal());
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setUser(user);
        newOrder.setCart(cart);

        return orderRepository.save(newOrder);
    }
}