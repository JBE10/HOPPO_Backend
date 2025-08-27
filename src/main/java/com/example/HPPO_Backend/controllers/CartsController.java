package com.example.HPPO_Backend.controllers;

import com.example.HPPO_Backend.entity.Cart;
import com.example.HPPO_Backend.entity.dto.CartRequest;
import com.example.HPPO_Backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("carts")
public class CartsController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<List<Cart>> getCarts() {
        return ResponseEntity.ok(this.cartService.getCarts());
    }

    @GetMapping({"/{cartId}"})
    public ResponseEntity<Cart> getCartById(@PathVariable Long cartId) {
        Optional<Cart> result = this.cartService.getCartById(cartId);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Object> createCart(@RequestBody CartRequest cartRequest) {
        Cart result = this.cartService.createCart(cartRequest);
        return ResponseEntity.created(URI.create("/carts/" + result.getId())).body(result);
    }
}
