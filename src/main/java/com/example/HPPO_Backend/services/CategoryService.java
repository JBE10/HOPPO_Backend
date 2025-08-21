package com.example.HPPO_Backend.services;

public class CategoryService {
    public String getCategories() {
        CategoryService categoryService = new CategoryService();
        return categoryService.getCategories();
    }
    public String getCategoryById(String categoryId) {
        CategoryService categoryService = new CategoryService();
        return categoryService.getCategoryById(categoryId);
    }
    public String createCategory(String category) {
        CategoryService categoryService = new CategoryService();
        return categoryService.createCategory(category);
    }
}
