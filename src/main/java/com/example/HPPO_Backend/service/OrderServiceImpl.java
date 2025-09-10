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
        // Validar datos del request
        if (orderRequest.getAddress() == null || orderRequest.getAddress().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La dirección es obligatoria");
        }

        if (orderRequest.getShipping() == null || orderRequest.getShipping().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El método de envío es obligatorio");
        }

        // Buscar el carrito del usuario
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Carrito no encontrado para el usuario con ID: " + user.getId()));

        // Verificar si el carrito está vacío
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No se puede crear una orden con un carrito vacío");
        }

        // Calcular el total
        Double total = 0.0;
        for (CartProduct item : cart.getItems()) {
            Product product = item.getProduct();
            Double priceToUse = product.getPrice();

            // Aplicar descuento si existe
            if (product.getDiscount() != null && product.getDiscount() > 0 && product.getDiscount() < 100) {
                double discountMultiplier = 1 - (product.getDiscount() / 100.0);
                priceToUse = product.getPrice() * discountMultiplier;
            }
            total += priceToUse * item.getQuantity();
        }

        // Crear la nueva orden
        Order newOrder = new Order();
        newOrder.setAddress(orderRequest.getAddress().trim());
        newOrder.setShipping(orderRequest.getShipping().trim());
        newOrder.setTotal(total);
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setUser(user);
        newOrder.setCart(cart);
        newOrder.setStatus(OrderStatus.CREATED); // Establecer explícitamente el status

        // Log para debugging
        System.out.println("Creando orden para usuario ID: " + user.getId());
        System.out.println("Carrito ID: " + cart.getId());
        System.out.println("Total: " + total);
        System.out.println("Items en carrito: " + cart.getItems().size());

        // Guardar la orden ANTES de limpiar el carrito
        Order savedOrder = orderRepository.save(newOrder);

        // Verificar que se guardó correctamente
        if (savedOrder.getId() == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al guardar la orden en la base de datos");
        }

        System.out.println("Orden guardada con ID: " + savedOrder.getId());

        // Limpiar el carrito después de crear la orden
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
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La orden ya ha sido cancelada");
        }

        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }


    @Override
    public Page<Order> getMyOrders(User user, PageRequest pageRequest) {
        return orderRepository.findByUserIdOrderByOrderDateDesc(user.getId(), pageRequest);
    }
}