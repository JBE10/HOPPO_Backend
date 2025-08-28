package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Cart;
import com.example.HPPO_Backend.entity.User;                 // <-- entidad correcta
import com.example.HPPO_Backend.entity.dto.CartRequest;
import com.example.HPPO_Backend.repository.CartRepository;
import com.example.HPPO_Backend.repository.UserRepository;   // <-- repo de User
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public Page<Cart> getCarts(PageRequest pageable) {
        return cartRepository.findAll(pageable);
    }

    public Optional<Cart> getCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    public Cart createCart(CartRequest cartRequest) {
        // traer el user por ID
        User user = userRepository.findById(cartRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuario no encontrado: id=" + cartRequest.getUserId()));

        Cart cart = new Cart();
        cart.setQuantity(cartRequest.getQuantity());
        cart.setUser(user);  // <-- ahora compila

        return cartRepository.save(cart);
    }
}
