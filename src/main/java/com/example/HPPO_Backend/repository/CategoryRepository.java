package com.example.HPPO_Backend.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import com.example.HPPO_Backend.entity.Category;
import com.example.HPPO_Backend.exceptions.CategoryDuplicateException;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
//    public List<Category> getCategories();
//    public Optional<Category> getCategoryById(Long categoryId);
//    public Category createCategory( String description) throws CategoryDuplicateException;

}
