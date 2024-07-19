package com.scaler.productService.exceptions;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryNotFoundException extends Exception {
    
    private String message;

    public CategoryNotFoundException( String message) {
        super(message);
        
    }
}