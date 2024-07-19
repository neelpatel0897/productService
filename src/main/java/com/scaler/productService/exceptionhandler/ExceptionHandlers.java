package com.scaler.productService.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.scaler.productService.dtos.CategoryNotFoundExceptionDto;
import com.scaler.productService.dtos.ExceptionDto;
import com.scaler.productService.dtos.ProductNotFoundExceptionDto;
import com.scaler.productService.exceptions.CategoryNotFoundException;
import com.scaler.productService.exceptions.ProductNotFoundException;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(value=ArithmeticException.class)
    public ResponseEntity<ExceptionDto> handleArithmeticException() {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage("Something went wrong");
        exceptionDto.setResolution("Nothing can be done");

        ResponseEntity<ExceptionDto> responseEntity = new ResponseEntity<>(exceptionDto,
                HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResponseEntity<Void> handleArrayIndexOutOfBoundException() {
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProductNotFoundExceptionDto> handleProductNotFoundException(ProductNotFoundException exception) {
        ProductNotFoundExceptionDto dto = new ProductNotFoundExceptionDto();
        dto.setMessage("Product with " + exception.getId() + " not found");

        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<CategoryNotFoundExceptionDto> handleCategoryNotFoundException(CategoryNotFoundException exception) {
        CategoryNotFoundExceptionDto dto = new CategoryNotFoundExceptionDto();
        dto.setMessage(exception.getMessage());

        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
}