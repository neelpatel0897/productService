package com.scaler.productService.dtos;




import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FakeStoreCategoryDto {


    private String category;

   
    public FakeStoreCategoryDto(java.lang.String categories) {
        this.category = categories;
    }

}