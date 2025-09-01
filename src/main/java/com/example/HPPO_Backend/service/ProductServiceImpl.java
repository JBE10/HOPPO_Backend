package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Brand;
import com.example.HPPO_Backend.entity.Category;
import com.example.HPPO_Backend.entity.Product;
import com.example.HPPO_Backend.entity.dto.ProductRequest;
import com.example.HPPO_Backend.exceptions.ProductDuplicateException;
import com.example.HPPO_Backend.repository.BrandRepository;
import com.example.HPPO_Backend.repository.CategoryRepository;
import com.example.HPPO_Backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    public Page<Product> getProducts(PageRequest pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public Product createProduct(ProductRequest productRequest) throws ProductDuplicateException {

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
        Brand brand = brandRepository.findById(productRequest.getBrandId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca no encontrada"));

        List<Product> products = productRepository.findByName(productRequest.getName());
        if (products.isEmpty()) {
            Product product = new Product();
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());
            product.setDescription(productRequest.getDescription());
            product.setStock(productRequest.getStock());
            product.setBrand(brand);
            product.setCategory(category);
            return productRepository.save(product);
        }
        throw new ProductDuplicateException();
    }

    @Override
    public Product updateProduct(Long productId, ProductRequest productRequest) {
        // Busca la categoría y la marca por sus IDs
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
        Brand brand = brandRepository.findById(productRequest.getBrandId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca no encontrada"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setStock(productRequest.getStock());
        product.setBrand(brand);
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
        productRepository.delete(product);
    }
}