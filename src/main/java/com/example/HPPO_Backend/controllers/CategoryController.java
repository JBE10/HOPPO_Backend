package com.example.HPPO_Backend.controllers;

import com.example.HPPO_Backend.entities.Category;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("categories")
public class CategoryController {
    @GetMapping
    public String getCategories() {
        return "List of categories";
    }
    @GetMapping("{categoryId}")
    public String getCategoryById(@PathVariable String categoryId) {
        return "Category with ID: " + categoryId;
    }
    @PostMapping()
    public String createCategory(@RequestBody String category) {
        return category;
    }
}
