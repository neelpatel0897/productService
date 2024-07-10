package com.scaler.productService.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scaler.productService.exceptions.ProductNotFoundException;
import com.scaler.productService.models.Product;





@Service
public interface ProductService {
    Product getProductById(Long id) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Product updateProduct(Long id, Product product);

    Product replaceProduct(Long id, Product product);

    Product createProduct(Product product);

    Product deleteProduct(Long id) throws ProductNotFoundException;
}