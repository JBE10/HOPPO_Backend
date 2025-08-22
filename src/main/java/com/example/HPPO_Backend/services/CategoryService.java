package com.example.HPPO_Backend.services;

import com.example.HPPO_Backend.entities.Category;
import com.example.HPPO_Backend.repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.Optional;

public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService() {
        categoryRepository = new CategoryRepository();
    }

    public ArrayList<Category> getCategories() {
        return categoryRepository.getCategories();
    }

    public Optional<Category> getCategoryById(int categoryId) {
        return categoryRepository.getCategoryById(categoryId);
    }

    public Category createCategory(int categoryId, String Description) {

        ArrayList<Category> categories = new CategoryRepository().getCategories();
        if(categories.stream().anyMatch((c) -> c.getId() == categoryId)) {
            throw new IllegalArgumentException("Category with this ID already exists");
        } else {
            Category category = Category.builder().id(categoryId).description(Description).build();
            return categoryRepository.createCategory(category);
        }
    }
}
