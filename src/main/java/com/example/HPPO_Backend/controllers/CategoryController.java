package com.example.HPPO_Backend.controllers;

import com.example.HPPO_Backend.entities.Category;
import com.example.HPPO_Backend.services.CategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("categories")
public class CategoryController {

    @GetMapping
    public String getCategories() {
        CategoryService categoryService = new CategoryService();
        return  categoryService.getCategories();
    }
    @GetMapping("{categoryId}")
    public String getCategoryById(@PathVariable String categoryId) {
        CategoryService categoryService = new CategoryService();
        return categoryService.getCategoryById(categoryId);
    }
    @PostMapping()
    public String createCategory(@RequestBody String category) {
        CategoryService categoryService = new CategoryService();

        return categoryService.createCategory(category);
    }
}
