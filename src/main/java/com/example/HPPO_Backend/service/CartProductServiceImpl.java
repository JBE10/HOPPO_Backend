package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.CartProduct;
import com.example.HPPO_Backend.entity.dto.CartProductRequest;
import com.example.HPPO_Backend.repository.CartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartProductServiceImpl implements CartProductService {
    @Autowired
    private CartProductRepository cartProductRepository;

    public List<CartProduct> getCartProducts() {
        return cartProductRepository.findAll();
    }

    public Optional<CartProduct> getCartProductById(Long id) {
        return cartProductRepository.findById(id);
    }

    public CartProduct createCartProduct(CartProductRequest request) {
        CartProduct cp = new CartProduct();
//        cp.setQuantity(request.getQuantity());
//        cp.setProductId(request.getProductId());
//        cp.setCartId(request.getCartId());
        return cartProductRepository.save(cp);
    }
}
