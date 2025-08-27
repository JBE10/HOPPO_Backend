package com.example.HPPO_Backend.controllers;

import com.example.HPPO_Backend.entity.Product;
import com.example.HPPO_Backend.entity.dto.ProductRequest;
import com.example.HPPO_Backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("products")
public class ProductsController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(@RequestParam(required = false) Integer page,
                                                     @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(this.productService.getProducts(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(this.productService.getProducts(PageRequest.of(page, size)));
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
