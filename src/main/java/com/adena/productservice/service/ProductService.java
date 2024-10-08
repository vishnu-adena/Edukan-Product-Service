package com.adena.productservice.service;

import com.adena.productservice.Exceptions.ProductNotFound;
import com.adena.productservice.dto.ImageDTO;
import com.adena.productservice.dto.RequestDTO;
import com.adena.productservice.dto.ResponseDTO;
import com.adena.productservice.models.Category;
import com.adena.productservice.models.Product;
import com.adena.productservice.repositories.CategoryRepository;
import com.adena.productservice.repositories.ProductRespository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Qualifier("DBProductService")
public class ProductService implements IProductService {

    ProductRespository productRepository;
    CategoryRepository categoryRepository;
    public ProductService(ProductRespository productRepository, CategoryRepository categoryRepository  ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Optional<ResponseDTO> getProductById(long id) throws ProductNotFound{
        Optional<Product> product = productRepository.findByisDeletedAndId(false,id);
        if (product.isEmpty()){
            throw new ProductNotFound("Product not Found with id "+id);
        }
        Product product1 = product.get();
        ResponseDTO responseDTO = modifyProduct(product1);
        return Optional.ofNullable(responseDTO);
    }

    @Override
    public List<ResponseDTO> getAllProducts() {

        List<Product> products = productRepository.findAll();
        List<ResponseDTO> responseDTO = new ArrayList<>();
        for (Product product : products){
            responseDTO.add(modifyProduct(product));
        }

        return responseDTO;
    }



    @Override
    public ResponseDTO replaceProduct(long id, RequestDTO requestDTO) throws ProductNotFound {
        Optional<Product> product = productRepository.findById(id);
        Product product1 ;
        if (product.isEmpty()){
            throw new ProductNotFound("Product not Found with id "+id);
        }
        //product.get().setImageUrl(requestDTO.getImage());
        product.get().setName(requestDTO.getTitle());
        product.get().setPrice(requestDTO.getPrice());
        product.get().setDescription(requestDTO.getDescription());
        Category category;
        Optional<Category> category1 = categoryRepository.findByName(requestDTO.getCategory());
        if (category1.isEmpty()){
            category = new Category();
            category.setName(requestDTO.getCategory());
            Category category2 = categoryRepository.save(category);
        }
        else {
            category = category1.get();
        }
        product.get().setCategory(category);
        Product product2 = productRepository.save(product.get());
        return modifyProduct(product2);
    }

    @Override
    public List<String> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<String> categoriesNames = new ArrayList<>();
        for (Category category : categories) {
            categoriesNames.add(category.getName());
        }
        return categoriesNames;

    }

    @Override
    public ResponseDTO UpdateProduct(long id, RequestDTO requestDTO) throws ProductNotFound {
        Optional<Product> product = productRepository.findByisDeletedAndId(false,id);
        if (product.isEmpty()){
            throw new ProductNotFound("Product not Found with id "+id);
        }
        product.get().setName(requestDTO.getTitle() == null ? product.get().getName() : requestDTO.getTitle());
        product.get().setDescription(requestDTO.getDescription() == null ? product.get().getDescription() : requestDTO.getDescription());
       // product.get().setImageUrl(requestDTO.getImage() == null ? product.get().getImageUrl() : requestDTO.getImage());
        product.get().setPrice(requestDTO.getPrice() <= 0 ? product.get().getPrice() : requestDTO.getPrice());
        Category category;
        Optional<Category> category1 = categoryRepository.findByName(requestDTO.getCategory());
        if (category1.isEmpty()){
             Category category2 = new Category();
             category2.setName(requestDTO.getCategory());
            category = categoryRepository.save(category2);
        }
        else {
            category = category1.get();
        }
        product.get().setCategory(requestDTO.
                getCategory()==null ? product.get().getCategory() : category);

        Product product2 = productRepository.save(product.get());
        return modifyProduct(product2);
    }

    public ResponseDTO modifyProduct(Product product){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setName(product.getName());
        responseDTO.setDescription(product.getDescription());
        responseDTO.setPrice(product.getPrice());
        responseDTO.setImage(product.getImage());
        responseDTO.setId(product.getId());
        responseDTO.setUpdated_At(product.getUpdated_At());
        responseDTO.setUpdated_At(product.getUpdated_At());
        responseDTO.setCategory(product.getCategory().getName());
        return responseDTO;
    }

    @Override
    public List<ResponseDTO> getProductsByCategory(String category) throws ProductNotFound{

        List<Product> products = productRepository.findAllByisDeletedAndcAndCategoryId(category);
        List <ResponseDTO>response = new ArrayList<>();
        for (Product product : products) {
            response.add(modifyProduct(product));
        }
        return response;
    }
    @Override
    public Product addNewProduct(RequestDTO request) throws ProductNotFound {

        Product product = new Product();
        product.setName(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());

        // Handle images
        List<ImageDTO> imageDTOs = request.getImage().stream().map(imageDTO -> {
            // Create ImageDTO objects as needed
            ImageDTO dto = new ImageDTO();
            dto.setPublicId(imageDTO.getPublicId());
            dto.setSecureUrl(imageDTO.getSecureUrl());
            dto.setDisplayName(imageDTO.getDisplayName());
            dto.setOriginalFileName(imageDTO.getOriginalFileName());
            return dto;
        }).collect(Collectors.toList());

        // Set images as JSON array
        product.setImage(imageDTOs);
        Optional<Category> category = categoryRepository.findByName(request.getCategory());
        Category savedCategory ;
        if (category.isEmpty()){
            Category newCategory = new Category();
            newCategory.setName(request.getCategory());
            savedCategory = categoryRepository.save(newCategory);
        }
        else {
            savedCategory = category.get();
        }
        product.setCategory(savedCategory);
        product.setUserId(getUserid());
        return productRepository.save(product);
    }

    private  long getUserid() {
        long userId;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        userId = jwt.getClaim("userId");
        return userId;
    }

    @Override
    public String deleteProduct(long id) throws ProductNotFound{
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()){
           throw new ProductNotFound("Product not Found with id "+id);
        }
        product.get().setDeleted(true);
        productRepository.save(product.get());
        return "Product Deleted Successfully";
    }

    @Override
    public List<ResponseDTO> getAllProductsByUserId() throws ProductNotFound {
        long userId = getUserid();
        List<Product> products = productRepository.findProductsByUserIdAndDeleted(userId,false);
        List <ResponseDTO>responseDTO = new ArrayList<ResponseDTO>();
        for (Product product : products){
            responseDTO.add(modifyProduct(product));
        }

        return responseDTO;
    }

}
