package com.example.HPPO_Backend.service;
 
import com.example.HPPO_Backend.entity.Product;
import com.example.HPPO_Backend.entity.dto.ProductRequest;
import com.example.HPPO_Backend.exceptions.ProductDuplicateException;
import com.example.HPPO_Backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.Optional;
 
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
 
    public Page<Product> getProducts(PageRequest pageable) {
        return productRepository.findAll(pageable);
    }
 
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }
 
    public Product createProduct(ProductRequest productRequest) throws ProductDuplicateException {
        List<Product> products = productRepository.findByName(productRequest.getName());
        if (products.isEmpty()) {
            Product product = new Product();
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());
            product.setDescription(productRequest.getDescription());
            product.setStock(productRequest.getStock());
            product.setBrandId(productRequest.getBrandId());
            product.setCategoryId(productRequest.getCategoryId());
            return productRepository.save(product);
        }
        throw new ProductDuplicateException();
    }
}