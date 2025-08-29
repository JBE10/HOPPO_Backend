package com.example.HPPO_Backend.controllers;

import com.example.HPPO_Backend.entity.CartProduct;
import com.example.HPPO_Backend.entity.dto.CartProductRequest;
import com.example.HPPO_Backend.service.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("cart-products")
public class CartProductsController {
    @Autowired
    private CartProductService cartProductService;

    @GetMapping
    public ResponseEntity<List<CartProduct>> getCartProducts() {
        return ResponseEntity.ok(this.cartProductService.getCartProducts());
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<CartProduct> getCartProductById(@PathVariable Long id) {
        Optional<CartProduct> result = this.cartProductService.getCartProductById(id);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Object> createCartProduct(@RequestBody CartProductRequest request) {
        CartProduct result = this.cartProductService.createCartProduct(request);
        return ResponseEntity.created(URI.create("/cart-products/" + result.getId())).body(result);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartProduct(@PathVariable Long id) {
        this.cartProductService.deleteCartProduct(id);
        return ResponseEntity.noContent().build();
    }
}
