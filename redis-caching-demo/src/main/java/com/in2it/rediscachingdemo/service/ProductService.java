package com.in2it.rediscachingdemo.service;

import com.in2it.rediscachingdemo.dto.ProductDto;

public interface ProductService {

    // Add a new product
    ProductDto addProduct(ProductDto product);

    // Update an existing product
    ProductDto updateProduct(String productId, ProductDto product);

    // Delete a product
    void deleteProduct(String productId);

    // Get a product by its ID
    ProductDto getProductById(String productId);

    // Get all products
    Iterable<ProductDto> getAllProducts();

}