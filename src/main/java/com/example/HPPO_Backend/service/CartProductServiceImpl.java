package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Cart;
import com.example.HPPO_Backend.entity.CartProduct;
import com.example.HPPO_Backend.entity.Product;
import com.example.HPPO_Backend.entity.dto.CartProductRequest;
import com.example.HPPO_Backend.repository.CartProductRepository;
import com.example.HPPO_Backend.repository.CartRepository;
import com.example.HPPO_Backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- ¡Importante!
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CartProductServiceImpl implements CartProductService {
    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;

    public List<CartProduct> getCartProducts() {
        return cartProductRepository.findAll();
    }

    public Optional<CartProduct> getCartProductById(Long id) {
        return cartProductRepository.findById(id);
    }


    public CartProduct createCartProduct(CartProductRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));


        if (product.getStock() < request.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No hay suficiente stock para el producto: " + product.getName());
        }


        int newStock = product.getStock() - request.getQuantity();
        product.setStock(newStock);
        productRepository.save(product); // Guardamos el producto actualizado


        CartProduct cp = new CartProduct();
        cp.setQuantity(request.getQuantity());
        cp.setProduct(product);
        cp.setCart(cart);

        return cartProductRepository.save(cp);
    }
}