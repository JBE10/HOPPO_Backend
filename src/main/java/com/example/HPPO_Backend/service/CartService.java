package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Cart;
import com.example.HPPO_Backend.entity.dto.CartRequest;

import java.util.List;
import java.util.Optional;

public interface CartService {
    List<Cart> getCarts();
    Optional<Cart> getCartById(Long cartId);
    Cart createCart(CartRequest cartRequest);
}
