package com.example.HPPO_Backend.controllers.ecom;

import com.example.HPPO_Backend.entity.Cart;
import com.example.HPPO_Backend.entity.Order;
import com.example.HPPO_Backend.entity.User;
import com.example.HPPO_Backend.entity.dto.CartRequest;
import com.example.HPPO_Backend.entity.dto.OrderRequest;
import com.example.HPPO_Backend.service.OrderService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("orders")
public class OrdersController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<Page<Order>> getOrders(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size){
        if (page == null || size == null)
            return ResponseEntity.ok(orderService.getOrders(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(orderService.getOrders(PageRequest.of(page, size)));
    }

    @GetMapping({"/{orderId}"})
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Optional<Order> result = this.orderService.getOrderById(orderId);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Object> createOrder(
            @RequestBody OrderRequest orderRequest,
            @AuthenticationPrincipal User user) {
        Order result = this.orderService.createOrder(orderRequest, user);
        return ResponseEntity.created(URI.create("/orders/" + result.getId())).body(result);
    }
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId) {
        Order cancelledOrder = this.orderService.cancelOrder(orderId);
        return ResponseEntity.ok(cancelledOrder);
    }

}