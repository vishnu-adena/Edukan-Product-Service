package com.adena.productservice;


import com.adena.productservice.dto.ResponseDTO;
import com.adena.productservice.models.Category;
import com.adena.productservice.models.Product;
import com.adena.productservice.repositories.CategoryRepository;
import com.adena.productservice.repositories.ProductRespository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@SpringBootTest
class ProductServiceApplicationTests {

    ProductRespository productRepository;
    CategoryRepository categoryRepository;
    RestTemplate restTemplate ;


    @Autowired
    public void FakeStoreProductService(ProductRespository productRepository,CategoryRepository categoryRepository,RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public Product getSinglefromFakeStore(ResponseDTO responseDTO){
        Product product = new Product();
        product.setId(responseDTO.getId());
        product.setName(responseDTO.getTitle());
        product.setDescription(responseDTO.getDescription());
        product.setPrice(responseDTO.getPrice());
        product.setImageUrl(responseDTO.getImage());
        Category category = new Category();
        category.setName(responseDTO.getCategory());
        product.setCategory(category);
        return product;
    }


    public Optional<Product> getSingleProduct(long id) {
        Product product ;
        ResponseDTO responseDTO = restTemplate.getForObject("https://fakestoreapi.com/products/"+id,
                ResponseDTO.class);
        product = getSinglefromFakeStore(responseDTO);

        Optional<Category> category = categoryRepository.findByName(product.getCategory().getName());
        Category savedCategory ;

        if (category.isEmpty()){
            Category newCategory = new Category();
            newCategory.setName(product.getCategory().getName());
            newCategory.setId(id);
            savedCategory = categoryRepository.save(newCategory);
        }
        else {
            savedCategory = category.get();
        }
        product.setCategory(savedCategory);
        Product savedProduct = productRepository.save(product);
        return Optional.of(savedProduct);
    }
    @Test
    void contextLoads() {
    }
    @Test
    void setAllProducts() {
        int i = 1;
        while (i < 21) {
            getSingleProduct(i);
            i = i + 1;
        }
    }
}

