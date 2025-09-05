package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Cart;
import com.example.HPPO_Backend.entity.User;                 // <-- entidad correcta
import com.example.HPPO_Backend.entity.dto.CartRequest;
import com.example.HPPO_Backend.repository.CartRepository;
import com.example.HPPO_Backend.repository.UserRepository;   // <-- repo de User
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        if (cartRequest.getUserId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId es obligatorio");


        Optional<Cart> existing = cartRepository.findByUserId(cartRequest.getUserId());
        if (existing.isPresent()) {
            return existing.get(); // idempotente: devolvés el existente
            // alternativo: throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya tiene carrito");
        }

        User user = userRepository.findById(cartRequest.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado: id=" + cartRequest.getUserId()));

        Cart cart = new Cart();
        cart.setQuantity(cartRequest.getQuantity() == null ? 0 : cartRequest.getQuantity());
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

}
