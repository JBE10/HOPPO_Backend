package com.example.HPPO_Backend.service;
 
import com.example.HPPO_Backend.entity.Cart;
import com.example.HPPO_Backend.entity.User;
import com.example.HPPO_Backend.entity.dto.CartRequest;
import com.example.HPPO_Backend.repository.CartRepository;
import com.example.HPPO_Backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
 
import java.util.Optional;
 
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;
 
    public Page<Cart> getCarts(PageRequest pageable) {
        return cartRepository.findAll(pageable);
    }
 
    public Optional<Cart> getCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }
 
    public Cart createCart(CartRequest cartRequest) {
    Cart cart = new Cart();
    cart.setQuantity(cartRequest.getQuantity());

    User user = userRepository.findById(cartRequest.getUserId())
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    cart.setUser(user);

    return cartRepository.save(cart);
    }
}