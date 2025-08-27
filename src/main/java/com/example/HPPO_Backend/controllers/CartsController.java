package com.example.HPPO_Backend.controllers;

import com.example.HPPO_Backend.entity.Cart;
import com.example.HPPO_Backend.entity.dto.CartRequest;
import com.example.HPPO_Backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net URI;
import java.util.Optional;

@RestController
@RequestMapping("carts")
public class CartsController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<Page<Cart>> getCarts(@RequestParam(required = false) Integer page,
                                               @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(this.cartService.getCarts(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(this.cartService.getCarts(PageRequest.of(page, size)));
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
