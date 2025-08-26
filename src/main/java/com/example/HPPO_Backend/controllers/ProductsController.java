package com.example.HPPO_Backend.controllers;

import com.example.HPPO_Backend.entity.Product;
import com.example.HPPO_Backend.entity.dto.ProductRequest;
import com.example.HPPO_Backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("products")
public class ProductsController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(this.productService.getProducts());
    }

    @GetMapping({"/{productId}"})
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Optional<Product> result = this.productService.getProductById(productId);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody ProductRequest productRequest) throws Exception {
        Product result = this.productService.createProduct(productRequest);
        return ResponseEntity.created(URI.create("/products/" + result.getId())).body(result);
    }
}
