package com.java.assignment.controller.message.mapper;

import com.java.assignment.controller.message.ProductRequestMessage;
import com.java.assignment.controller.message.ProductResponse;
import com.java.assignment.model.Product;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Component;

@Component
public class ProductMapper
{
    public Product toProduct(@Valid ProductRequestMessage requestMessage)
    {
        Product product = new Product();
        product.setDescription(requestMessage.getDescription());
        product.setName(requestMessage.getName());
        product.setPrice(requestMessage.getPrice());
        return product;
    }


    public List<ProductResponse> toResponseList(List<Product> allProducts)
    {
        return allProducts.stream().map(this::getProductResponse).collect(Collectors.toList());
    }


    public ProductResponse getProductResponse(Product product)
    {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setDescription(product.getDescription());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        return productResponse;
    }
}
