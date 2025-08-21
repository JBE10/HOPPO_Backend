package com.example.HPPO_Backend.services;

import com.example.HPPO_Backend.entities.Category;
import com.example.HPPO_Backend.repositories.CategoryRepository;

import java.util.ArrayList;

public class CategoryService {
    public ArrayList<Category> getCategories() {
        CategoryRepository categoryRepository = new CategoryRepository();
        return categoryRepository.getCategories();
    }
    public Category getCategoryById(int categoryId) {
        CategoryRepository categoryRepository = new CategoryRepository();
        return categoryRepository.getCategoryById(categoryId);
    }
    public Category createCategory(Category category) {
        CategoryRepository categoryRepository = new CategoryRepository();
        return categoryRepository.createCategory(category);
    }
}
