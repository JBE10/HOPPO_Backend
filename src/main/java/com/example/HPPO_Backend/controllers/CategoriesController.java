package com.example.HPPO_Backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.HPPO_Backend.entity.Category;
import com.example.HPPO_Backend.entity.dto.CategoryRequest;
import com.example.HPPO_Backend.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("categories")



public class CategoriesController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(this.categoryService.getCategories());
    }

    @GetMapping({"/{categoryId}"})
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) {
        Optional<Category> result = this.categoryService.getCategoryById(categoryId);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

//    @PostMapping
//    public ResponseEntity<Object> createCategory(@RequestBody CategoryRequest categoryRequest) throws Exception {
//        Category result = this.categoryService.createCategory(categoryRequest.getId(), categoryRequest.getDescription());
//        return ResponseEntity.created(URI.create("/categories/" + result.getId())).body(result);
//    }


}
