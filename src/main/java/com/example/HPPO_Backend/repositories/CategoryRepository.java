package com.example.HPPO_Backend.repositories;

import com.example.HPPO_Backend.entities.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class CategoryRepository {
    public ArrayList<Category> categories = new ArrayList<>(Arrays.asList(
            Category.builder().description("Motherboard").id(1).build(),
            Category.builder().description("GPU").id(2).build())
    );
    public ArrayList<Category> getCategories() {
        return this.categories;
    }

    public Optional<Category> getCategoryById(int id) {
        if (categories == null) {
            return Optional.empty();
        }
        return categories.stream()
                .filter(category -> category != null && category.getId() == id)
                .findFirst();
    }
    public Category createCategory(Category category) {
        categories.add(category);
        return category;
    }


}
