package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.*;
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

    @Override
    @Transactional
    public Order createOrder(OrderRequest orderRequest, User user) {


        Cart cart = cartRepository.findById(orderRequest.getCartId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));


        if (!cart.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para usar este carrito.");
        }

        if(orderRepository.findByCartId(orderRequest.getCartId()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El carrito ya se encuentra en una orden.");
        }

        Double total = 0.0;
        for (CartProduct item : cart.getItems()) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }

        Order newOrder = new Order();
        newOrder.setAddress(orderRequest.getAddress());
        newOrder.setShipping(orderRequest.getShipping());
        newOrder.setTotal(total);
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setUser(user);
        newOrder.setCart(cart);

        return orderRepository.save(newOrder);
    }
    @Override
    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada"));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La orden ya ha sido cancelada.");
        }
        for (CartProduct item : order.getCart().getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());

        }
        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

}