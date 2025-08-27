package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Cart;
import com.example.HPPO_Backend.entity.dto.CartRequest;
import com.example.HPPO_Backend.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    public Page<Cart> getCarts(PageRequest pageRequest) {
        return cartRepository.findAll(pageRequest);
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
