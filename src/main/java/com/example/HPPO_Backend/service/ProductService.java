package com.example.HPPO_Backend.service;
 
import com.example.HPPO_Backend.entity.Product;
import com.example.HPPO_Backend.entity.dto.ProductRequest;
import com.example.HPPO_Backend.exceptions.ProductDuplicateException;
 
import java.util.Optional;
 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
 
public interface ProductService {
    public Page<Product> getProducts(PageRequest pageRequest);
    Optional<Product> getProductById(Long productId);
    Product createProduct(ProductRequest productRequest) throws ProductDuplicateException;
}