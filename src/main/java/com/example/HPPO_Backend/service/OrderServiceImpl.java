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

        if (orderRequest.getAddress() == null || orderRequest.getAddress().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La dirección es obligatoria");
        }

        if (orderRequest.getShipping() == null || orderRequest.getShipping().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El método de envío es obligatorio");
        }


        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Carrito no encontrado para el usuario con ID: " + user.getId()));


        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No se puede crear una orden con un carrito vacío");
        }


        Order newOrder = new Order();
        newOrder.setAddress(orderRequest.getAddress().trim());
        newOrder.setShipping(orderRequest.getShipping().trim());
        newOrder.setUser(user);
        newOrder.setStatus(OrderStatus.CREATED);
        newOrder.setOrderDate(LocalDateTime.now());

        double total = 0.0;
        for (CartProduct cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();
            double priceAtPurchase = product.getPrice();


            if (product.getDiscount() != null && product.getDiscount() > 0 && product.getDiscount() < 100) {
                double discountMultiplier = 1 - (product.getDiscount() / 100.0);
                priceAtPurchase = product.getPrice() * discountMultiplier;
            }
            total += priceAtPurchase * cartItem.getQuantity();


            OrderItem orderItem = new OrderItem(product, cartItem.getQuantity(), priceAtPurchase, newOrder);
            newOrder.getItems().add(orderItem);
        }

        newOrder.setTotal(total);


        Order savedOrder = orderRepository.save(newOrder);


        cart.getItems().clear();
        cart.setQuantity(0);
        cartRepository.save(cart);

        return savedOrder;
    }

    @Override
    @Transactional
    public Order cancelOrder(Long orderId, User user) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada"));


        if (!order.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para cancelar esta orden");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La orden ya ha sido cancelada");
        }

        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }



    @Override
    public Page<Order> getMyOrders(User user, PageRequest pageRequest) {
        return orderRepository.findByUserIdOrderByOrderDateDesc(user.getId(), pageRequest);
    }

    @Override
    @Transactional
    public Order updateOrder(Long orderId, OrderRequest orderRequest, User user) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada"));


        if (!order.getUser().getId().equals(user.getId()) &&
                !user.getRole().equals(Role.VENDEDOR)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para actualizar esta orden");
        }


        if (orderRequest.getAddress() != null && !orderRequest.getAddress().trim().isEmpty()) {
            order.setAddress(orderRequest.getAddress().trim());
        }


        if (orderRequest.getShipping() != null && !orderRequest.getShipping().trim().isEmpty()) {
            order.setShipping(orderRequest.getShipping().trim());
        }


        if (orderRequest.getStatus() != null) {
            order.setStatus(orderRequest.getStatus());
        }

        return orderRepository.save(order);
    }
}
