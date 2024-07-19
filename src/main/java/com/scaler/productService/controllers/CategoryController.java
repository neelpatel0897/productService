package com.scaler.productService.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.scaler.productService.dtos.FakeStoreCategoryDto;
import com.scaler.productService.exceptions.CategoryNotFoundException;
import com.scaler.productService.models.Category;
import com.scaler.productService.models.Product;
import com.scaler.productService.services.CategoryService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v1")
public class CategoryController {
    
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<FakeStoreCategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/category/{categoryName}")
    public List<Product> getMethodName(@PathVariable("categoryName") String categoryName) throws CategoryNotFoundException {
        return categoryService.getCategoryByname(categoryName);
    }
    
}