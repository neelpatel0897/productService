package com.scaler.productService.models;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category extends BaseModel{
    private String title;  
    //    @OneToMany(mappedBy = "category") // (fetch = FetchType.EAGER)
//    private List<Product> products;
}
