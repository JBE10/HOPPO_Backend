package com.example.HPPO_Backend.controllers;

import com.example.HPPO_Backend.entity.Order;
import com.example.HPPO_Backend.entity.dto.OrderRequest;
import com.example.HPPO_Backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("orders")
public class OrdersController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(this.orderService.getOrders());
    }

    @GetMapping({"/{orderId}"})
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Optional<Order> result = this.orderService.getOrderById(orderId);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody OrderRequest orderRequest) {
        Order result = this.orderService.createOrder(orderRequest);
        return ResponseEntity.created(URI.create("/orders/" + result.getId())).body(result);
    }
}
