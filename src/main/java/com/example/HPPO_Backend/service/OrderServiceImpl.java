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
import java.util.ArrayList;
import java.util.List;
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
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado para el usuario"));

        // Verificar si el carrito está vacío
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede crear una orden con un carrito vacío.");
        }



        Double total = 0.0;
        for (CartProduct item : cart.getItems()) {
            Product product = item.getProduct();
            Double priceToUse = product.getPrice();
            if (product.getDiscount() != null && product.getDiscount() > 0 && product.getDiscount() < 100) {
                double discountMultiplier = 1 - (product.getDiscount() / 100.0);
                priceToUse = product.getPrice() * discountMultiplier;
            }
            total += priceToUse * item.getQuantity();
        }

        Order newOrder = new Order();
        newOrder.setAddress(orderRequest.getAddress());
        newOrder.setShipping(orderRequest.getShipping());
        newOrder.setTotal(total);
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setUser(user);
        newOrder.setCart(cart);

        Order savedOrder = orderRepository.save(newOrder);

        // Vaciar el carrito después de crear la orden
        cart.getItems().clear();
        cart.setQuantity(0);
        cartRepository.save(cart);

        return savedOrder;
    }

    @Override
    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada"));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La orden ya ha sido cancelada.");
        }


        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}