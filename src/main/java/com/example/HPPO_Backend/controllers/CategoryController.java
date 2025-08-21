package com.example.HPPO_Backend.controllers;

import com.example.HPPO_Backend.entities.Category;
import com.example.HPPO_Backend.services.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("categories")
public class CategoryController {

    @GetMapping
    public ArrayList<Category> getCategories() {
        CategoryService categoryService = new CategoryService();
        return  categoryService.getCategories();
    }
    @GetMapping("{categoryId}")
    public Category getCategoryById(@PathVariable int categoryId) {
        CategoryService categoryService = new CategoryService();
        return categoryService.getCategoryById(categoryId);
    }
    @PostMapping()
    public Category createCategory(@RequestBody Category category) {
        CategoryService categoryService = new CategoryService();
        categoryService.createCategory(category);
        return categoryService.createCategory(category);
    }
}
