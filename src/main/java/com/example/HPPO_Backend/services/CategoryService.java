package com.example.HPPO_Backend.services;

public class CategoryService {
    public String getCategories() {
        return "List of categories";
    }
    public String getCategoryById(String categoryId) {
        return "Category with ID: " + categoryId;
    }
    public String createCategory(String category) {
        return category;
    }
}
