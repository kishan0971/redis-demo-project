package com.in2it.rediscachingdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in2it.rediscachingdemo.dto.ProductDto;
import com.in2it.rediscachingdemo.exception.ProductNotFoundException;
import com.in2it.rediscachingdemo.service.ProductService;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDTO) {
    	ProductDto createdProduct = productService.addProduct(productDTO);
        return ResponseEntity.status(201).body(createdProduct);
    }
    
    
    @PutMapping("/{productId}") // HTTP PUT method for updating a product
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable String productId, // Extracts productId from the URL
            @RequestBody ProductDto product) { // Accepts the updated product data in the request body
        
        try {
            ProductDto updatedProduct = productService.updateProduct(productId, product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if product not found
        }
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String productId) {
    	System.out.println("from controller");
        ProductDto productDto = productService.getProductById(productId);
        return ResponseEntity.ok(productDto);
    }
    
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }


}