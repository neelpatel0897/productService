package com.scaler.productService.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scaler.productService.exceptions.ProductNotFoundException;
import com.scaler.productService.models.Product;
import com.scaler.productService.services.ProductService;


@RestController
@RequestMapping("api/v1")
public class ProductController {

    //Field Injection
    // @Autowired--not recommended practice because everybody does not know spring    
    private ProductService productService;

    //Constructor Injection
    //Do not neeed to put autowired keyword in the constructor
    ProductController(@Qualifier("fakeStoreProductService")ProductService productService) {
        this.productService = productService;
    }

    // Setter Injection
    // @Autowired
    // public void setProductService(ProductService productService) {
    //     this.productService = productService;
    // }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id")Long id) throws ProductNotFoundException{
        return productService.getProductById(id);
    }
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable("id")Long id) throws ProductNotFoundException{
        return new ResponseEntity<Product>(productService.deleteProduct(id),HttpStatus.NOT_FOUND);

    }
    
    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) { // can use DTO as well.
        return productService.createProduct(product);
    }

    @PutMapping("/products/{id}")
    public Product updateProductById(@RequestBody Product product,@PathVariable("id")Long id){
        return productService.updateProduct(id,product);

    }

    
    
}