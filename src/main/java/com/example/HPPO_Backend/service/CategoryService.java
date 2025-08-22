package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public ArrayList<Category> getCategories();
    public Optional<Category> getCategoryById(int categoryId);
    public Category createCategory(int newCategoryId, String description) throws Exception;
}
