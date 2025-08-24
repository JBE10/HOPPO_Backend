package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public List<Category> getCategories();
    public Optional<Category> getCategoryById(Long categoryId);
//    public Category createCategory(Long newCategoryId, String description) throws Exception;
}
