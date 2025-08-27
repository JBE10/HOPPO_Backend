package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Order;
import com.example.HPPO_Backend.entity.User;
import com.example.HPPO_Backend.entity.dto.OrderRequest;
import com.example.HPPO_Backend.repository.OrderRepository;
import com.example.HPPO_Backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        // 1) Traer el usuario dueño de la orden
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: id=" + orderRequest.getUserId()));

        // 2) Mapear el DTO → entidad
        Order newOrder = new Order();
        newOrder.setAddress(orderRequest.getAddress());
        newOrder.setShipping(orderRequest.getShipping());
        newOrder.setCartId(orderRequest.getCartId());
        newOrder.setUser(user); // <— clave: setear el objeto User

        // (Opcional: si agregaste total/fecha)
         newOrder.setTotal(orderRequest.getTotal());
         newOrder.setOrderDate(LocalDateTime.now());

        // 3) Guardar
        return orderRepository.save(newOrder);
    }
}
