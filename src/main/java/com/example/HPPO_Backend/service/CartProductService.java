package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.CartProduct;
import com.example.HPPO_Backend.entity.dto.CartProductRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface CartProductService {
    Page<CartProduct> getCartProducts(PageRequest pageRequest);
    Optional<CartProduct> getCartProductById(Long id);
    CartProduct createCartProduct(CartProductRequest request);
}
