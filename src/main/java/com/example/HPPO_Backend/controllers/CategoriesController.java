package com.example.HPPO_Backend.controllers;

import com.example.HPPO_Backend.entity.Category;
import com.example.HPPO_Backend.entity.dto.CategoryRequest;
import com.example.HPPO_Backend.exceptions.CategoryDuplicateException;
import com.example.HPPO_Backend.service.CategoryService;
import com.example.HPPO_Backend.service.CategoryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("categories")
public class CategoriesController {
    private CategoryServiceImpl categoryService ;
    public CategoriesController() {
        this.categoryService = new CategoryServiceImpl();
    }

    @GetMapping
    public ResponseEntity<ArrayList<Category>> getCategories() {
        return ResponseEntity.ok(this.categoryService.getCategories());
    }

    @GetMapping({"/{categoryId}"})
    public ResponseEntity<Category> getCategoryById(@PathVariable int categoryId) {
        Optional<Category> result = this.categoryService.getCategoryById(categoryId);
        return result.isPresent() ? ResponseEntity.ok((Category)result.get()) : ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody CategoryRequest categoryRequest) throws Exception {
        Category result = this.categoryService.createCategory(categoryRequest.getId(), categoryRequest.getDescription());
        return ResponseEntity.created(URI.create("/categories/" + result.getId())).body(result);
    }


}
