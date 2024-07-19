package com.scaler.productService.services;


import org.springframework.stereotype.Service;

import com.scaler.productService.dtos.FakeStoreCategoryDto;
import com.scaler.productService.exceptions.CategoryNotFoundException;

import com.scaler.productService.models.Product;

import java.util.List;

@Service
public interface CategoryService {
    
List<FakeStoreCategoryDto> getAllCategories();

List<Product> getCategoryByname(String categoryName) throws CategoryNotFoundException;
}