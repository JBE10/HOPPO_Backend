package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Product;
import com.example.HPPO_Backend.entity.dto.ProductRequest;
import com.example.HPPO_Backend.exceptions.ProductDuplicateException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface ProductService {
    Page<Product> getProducts(PageRequest pageRequest);
    Optional<Product> getProductById(Long productId);
    Product createProduct(ProductRequest productRequest) throws ProductDuplicateException;
}
