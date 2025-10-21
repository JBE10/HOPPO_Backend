package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Cart;
import com.example.HPPO_Backend.entity.User;
import com.example.HPPO_Backend.entity.dto.CartRequest;
import com.example.HPPO_Backend.repository.CartRepository;
import com.example.HPPO_Backend.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<Cart> getCarts(PageRequest pageable) {
        Page<Cart> page = cartRepository.findAll(pageable);
        LocalDateTime now = LocalDateTime.now();

        // Filtrar los carritos válidos (no expirados)
        List<Cart> valid = page.getContent().stream()
                .filter(cart -> cart.getExpiresAt() != null && cart.getExpiresAt().isAfter(now))
                .collect(Collectors.toList());

        // Eliminar los carritos expirados
        page.getContent().stream()
                .filter(cart -> cart.getExpiresAt() != null && cart.getExpiresAt().isBefore(now))
                .forEach(cartRepository::delete);

        return new PageImpl<>(valid, pageable, valid.size());
    }

    @Override
    public Optional<Cart> getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .flatMap(cart -> {
                    if (cart.getExpiresAt() == null || cart.getExpiresAt().isAfter(LocalDateTime.now())) {
                        return Optional.of(cart);
                    } else {
                        cartRepository.delete(cart);
                        return Optional.empty();
                    }
                });
    }

    @Override
    public Cart createCart(CartRequest cartRequest) {
        if (cartRequest.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId es obligatorio");
        }

        // Buscar carrito activo (no expirado)
        Optional<Cart> existingActive = cartRepository.findActiveCartByUserId(cartRequest.getUserId(), LocalDateTime.now());
        if (existingActive.isPresent()) {
            return existingActive.get();
        }

        // Si hay un carrito expirado, eliminarlo
        Optional<Cart> existingExpired = cartRepository.findByUserId(cartRequest.getUserId());
        if (existingExpired.isPresent() && existingExpired.get().isExpired()) {
            cartRepository.delete(existingExpired.get());
        }

        // Crear nuevo carrito
        User user = userRepository.findById(cartRequest.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado: id=" + cartRequest.getUserId()));

        Cart cart = new Cart();
        cart.setQuantity(cartRequest.getQuantity() == null ? 0 : cartRequest.getQuantity());
        cart.setUser(user);
        cart.extendExpiration(); // opcional, si querés que siempre arranque con fecha de expiración nueva

        return cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .flatMap(cart -> {
                    if (cart.getExpiresAt() == null || cart.getExpiresAt().isAfter(LocalDateTime.now())) {
                        return Optional.of(cart);
                    } else {
                        cartRepository.delete(cart);
                        return Optional.empty();
                    }
                });
    }

    @Override
    public Optional<Cart> getActiveCartByUserId(Long userId) {
        return cartRepository.findActiveCartByUserId(userId, LocalDateTime.now());
    }

    @Override
    @Transactional
    public void extendCartExpiration(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado: id=" + cartId));

        cart.extendExpiration();
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void cleanExpiredCarts() {
        List<Cart> expiredCarts = cartRepository.findExpiredCarts(LocalDateTime.now());
        cartRepository.deleteAll(expiredCarts);
    }

    @Override
    @Transactional
    public int deleteExpiredCarts() {
        List<Cart> expiredCarts = cartRepository.findExpiredCarts(LocalDateTime.now());
        int count = expiredCarts.size();
        cartRepository.deleteAll(expiredCarts);
        return count;
    }
}
