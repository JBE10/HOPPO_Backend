package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Category;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CategoryService {
    public Page<Category> getCategories(PageRequest pageRequest);
    public Optional<Category> getCategoryById(Long categoryId);
    public Category createCategory( String description) throws Exception;
}
