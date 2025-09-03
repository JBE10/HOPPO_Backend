package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Category;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CategoryService {
     Page<Category> getCategories(PageRequest pageRequest);
     Optional<Category> getCategoryById(Long categoryId);
     Category createCategory( String description) throws Exception;
     void deleteCategory(Long categoryId) throws Exception;
}
