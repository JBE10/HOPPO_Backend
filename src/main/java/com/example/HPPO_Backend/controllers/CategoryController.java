package com.example.HPPO_Backend.controllers;

import com.example.HPPO_Backend.entities.Category;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("categories")
public class CategoryController {

    @GetMapping
    public ArrayList<Category> getCategories() {
        ICategoryService categoryService = new ICategoryService();
        return  categoryService.getCategories();
    }
    @GetMapping("{categoryId}")
    public Category getCategoryById(@PathVariable int categoryId) {

        ICategoryService categoryService = new ICategoryService();
        return categoryService.getCategoryById(categoryId);
    }
    @PostMapping()
    public Category createCategory(@RequestBody Category category) {
        ICategoryService categoryService = new ICategoryService();
        categoryService.createCategory(category);
        return categoryService.createCategory(category);
    }
}
