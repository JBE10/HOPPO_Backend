package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Category;
import com.example.HPPO_Backend.exceptions.CategoryDuplicateException;
import com.example.HPPO_Backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public  class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public Category createCategory( String description) throws CategoryDuplicateException {
        List<Category> categories = categoryRepository.findByName(description);
        if (categories.isEmpty()) {
        return categoryRepository.save(new Category(description));
        }
        throw new CategoryDuplicateException();
    }
}
