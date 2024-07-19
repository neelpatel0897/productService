package com.scaler.productService.services;

import org.springframework.stereotype.Service;



import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.scaler.productService.dtos.FakeStoreProductDto;
import com.scaler.productService.exceptions.ProductNotFoundException;
import com.scaler.productService.models.Category;
import com.scaler.productService.models.Product;

import java.util.ArrayList;
import java.util.List;


@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {


    private RestTemplate restTemplate;
    private String specificProductRequestURL="https://fakestoreapi.com/products/{id}";
    private String productsRequestURL="https://fakestoreapi.com/products";

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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
  
    
     @Override
    public Product getProductById(Long id) throws ProductNotFoundException{
        //Call FakeStore API here to get the Product with given id.

        FakeStoreProductDto fakeStoreProductDto =restTemplate.getForObject(specificProductRequestURL,  FakeStoreProductDto.class);
        //1st param -> URL
        //2nd param -> Response

        if (fakeStoreProductDto == null) {
            
            throw new ProductNotFoundException(id, "Product with id " + id + " not found");
                       
        }

        //Convert FakeStore DTO into Product object.
        Product product = convertFakeStoreDtoToProduct(fakeStoreProductDto);

        return product;
    }


    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDtos =restTemplate.getForObject(productsRequestURL,  FakeStoreProductDto[].class);
        
        //convert List of FakeStoreProductDtos to List of Products
        List<Product> response = new ArrayList<>();
        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            response.add(convertFakeStoreDtoToProduct(fakeStoreProductDto));
        }
        
        
        return response;
    }


    @Override
    public Product updateProduct(Long id, Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setImage(product.getImage());
        fakeStoreProductDto.setDescription(product.getDescription());

        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDto, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreProductDto.class,
                        restTemplate.getMessageConverters());
        FakeStoreProductDto response =
                restTemplate.execute(specificProductRequestURL, HttpMethod.PUT, requestCallback, responseExtractor);

        return convertFakeStoreDtoToProduct(response);
    }


    @Override
    public Product replaceProduct(Long id, Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setImage(product.getImage());
        fakeStoreProductDto.setDescription(product.getDescription());

        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDto, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreProductDto.class,
                        restTemplate.getMessageConverters());
        FakeStoreProductDto response =
                restTemplate.execute(productsRequestURL + id, HttpMethod.PUT, requestCallback, responseExtractor);

        return convertFakeStoreDtoToProduct(response);
    }


    @Override
    public Product createProduct(Product product) {
        
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setImage(product.getImage());
        fakeStoreProductDto.setDescription(product.getDescription());

        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDto, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreProductDto.class,
                        restTemplate.getMessageConverters());
        FakeStoreProductDto response =
                restTemplate.execute(productsRequestURL, HttpMethod.POST, requestCallback, responseExtractor);

        return convertFakeStoreDtoToProduct(response);
    }


    @Override
    public Product deleteProduct(Long id)  throws ProductNotFoundException {
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
		ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
		ResponseEntity<FakeStoreProductDto> response= restTemplate.execute(specificProductRequestURL,HttpMethod.DELETE, requestCallback,responseExtractor,id);       
         
        FakeStoreProductDto fakeStoreProductDto=response.getBody();

        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException(id, "Product with id " + id + " not found");
        }

        return convertFakeStoreDtoToProduct(fakeStoreProductDto);
        
    }
}