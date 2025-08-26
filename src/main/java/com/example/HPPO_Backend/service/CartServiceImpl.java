package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Cart;
import com.example.HPPO_Backend.entity.dto.CartRequest;
import com.example.HPPO_Backend.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getCarts() {
        return cartRepository.findAll();
    }

    public Optional<Cart> getCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    public Cart createCart(CartRequest cartRequest) {
        Cart cart = new Cart();
        cart.setQuantity(cartRequest.getQuantity());
        cart.setUserId(cartRequest.getUserId());
        return cartRepository.save(cart);
    }
}
