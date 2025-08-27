package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Product;
import com.example.HPPO_Backend.entity.dto.ProductRequest;
import com.example.HPPO_Backend.exceptions.ProductDuplicateException;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getProducts();
    Optional<Product> getProductById(Long productId);
    Product createProduct(ProductRequest productRequest) throws ProductDuplicateException;
}
