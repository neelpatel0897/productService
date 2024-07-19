package com.scaler.productService.services;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.scaler.productService.dtos.FakeStoreCategoryDto;
import com.scaler.productService.dtos.FakeStoreProductDto;
import com.scaler.productService.exceptions.CategoryNotFoundException;
import com.scaler.productService.models.Category;
import com.scaler.productService.models.Product;

@Service("fakeStoreCategoryService")
public class FakeStoreCategoryService implements CategoryService{

    private RestTemplate restTemplate;
    private String specificCategotyRequestURL="https://fakestoreapi.com/products/category/";
    private String categoriesRequestURL="https://fakestoreapi.com/products/categories";

    public FakeStoreCategoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<FakeStoreCategoryDto> getAllCategories() {

        String[] categories = restTemplate.getForObject( categoriesRequestURL, String[].class);

        List<FakeStoreCategoryDto> response= new ArrayList<FakeStoreCategoryDto>();

        

        for(int i=0; i<categories.length; i++){
            FakeStoreCategoryDto fakeStoreCategoryDto = new FakeStoreCategoryDto(categories[i]);
            
            response.add(fakeStoreCategoryDto);
        }

        return response;
   
        
    }

    @Override
    public List<Product>  getCategoryByname(String categoryName) throws CategoryNotFoundException {
        FakeStoreProductDto[] fakeStoreProductDtos =restTemplate.getForObject(specificCategotyRequestURL+categoryName,  FakeStoreProductDto[].class);
        
        if(fakeStoreProductDtos.length==0){
            throw new CategoryNotFoundException("Category with name "+categoryName+" not found.");
        }
        //convert List of FakeStoreProductDtos to List of Products
        List<Product> response = new ArrayList<>();
        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            response.add(convertFakeStoreDtoToProduct(fakeStoreProductDto));
        }
        
        
        return response;
    }

    private Product convertFakeStoreDtoToProduct(FakeStoreProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setImage(dto.getImage());

        Category category = new Category();
        category.setTitle(dto.getCategory());
        product.setCategory(category);

        return product;
    }
    
}