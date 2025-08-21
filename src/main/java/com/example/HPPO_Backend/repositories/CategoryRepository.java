package com.example.HPPO_Backend.repositories;

import com.example.HPPO_Backend.entities.Category;

import java.util.ArrayList;
import java.util.Arrays;

public class CategoryRepository {
    public ArrayList<Category> categories = new ArrayList<>(Arrays.asList(
            Category.builder().description("Motherboard").id(1).build(),
            Category.builder().description("GPU").id(2).build())
    );
    public ArrayList<Category> getCategories() {
        return this.categories;
    }

    public Category getCategoryById(int id) {
        return categories.stream().filter(category -> category.getId() == id).findFirst().orElse(null);
    }

    public Category createCategory(Category category) {
        categories.add(category);
        return category;
    }


}
