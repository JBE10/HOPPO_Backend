package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.CartProduct;
import com.example.HPPO_Backend.entity.dto.CartProductRequest;

import java.util.List;
import java.util.Optional;

public interface CartProductService {
    List<CartProduct> getCartProducts();
    Optional<CartProduct> getCartProductById(Long id);
    CartProduct createCartProduct(CartProductRequest request);
    void deleteCartProduct(Long id);
}
