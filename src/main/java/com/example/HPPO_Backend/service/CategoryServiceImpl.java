package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Category;
import com.example.HPPO_Backend.exceptions.CategoryDuplicateException;
import com.example.HPPO_Backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl() {
        categoryRepository = new CategoryRepository();
    }

    public ArrayList<Category> getCategories() {
        return categoryRepository.getCategories();
    }

    public Optional<Category> getCategoryById(int categoryId) {
        return categoryRepository.getCategoryById(categoryId);
    }

    public Category createCategory(int newCategoryId, String description) throws CategoryDuplicateException {
        ArrayList<Category> categories = categoryRepository.getCategories();
        if (categories.stream().anyMatch(
                category -> category.getId() == newCategoryId && category.getDescription().equals(description)))
            throw new CategoryDuplicateException();
        return categoryRepository.createCategory(newCategoryId, description);
    }
}
