package com.in2it.rediscachingdemo.service.impl;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.in2it.rediscachingdemo.dto.ProductDto;
import com.in2it.rediscachingdemo.entity.Product;
import com.in2it.rediscachingdemo.exception.ProductNotFoundException;
import com.in2it.rediscachingdemo.repository.ProductMongoRepository;
import com.in2it.rediscachingdemo.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

//    @Autowired
//    private  ProductPostgresRepository productPostgresRepository;

	@Autowired
	RedisTemplate<String, Object> template;
	
    @Autowired
    private ProductMongoRepository productMongoRepository;

    @CachePut(value = "productDTO", key = "#productDTO.id")
    @Override
    public ProductDto addProduct(ProductDto productDTO) {

        Product product = new Product();
        BeanUtils.copyProperties(productDTO,product);
        product = productMongoRepository.save(product);
        
//        BeanUtils.copyProperties(product,productDTO);
//        template.opsForHash().put(product.getId(), "product", productDTO);
       
        template.expire(product.getId(), 3, TimeUnit.MINUTES);
        return productDTO;

    }


   
    @CachePut(value = "productDTO", key = "#productDto.id")
    @Override
    public ProductDto updateProduct(String productId, ProductDto productDto) {
        Product extProduct = productMongoRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("Product doesn't exist with given ID"));

        if (productDto.getName() != null && !productDto.getName().isEmpty()) {
            extProduct.setName(productDto.getName());
        }
        if (productDto.getPrice() != 0) {
            if (productDto.getPrice() < 0) {
                throw new IllegalArgumentException("Product price must be non-negative");
            }
            extProduct.setPrice(productDto.getPrice());
        }
        if (productDto.getDescription() != null) {
            extProduct.setDescription(productDto.getDescription());
        }

        Product updatedProduct = productMongoRepository.save(extProduct);
        BeanUtils.copyProperties(updatedProduct, productDto);
        return productDto;
    }

    @CacheEvict(key = "#productId",value = "productDTO", beforeInvocation = true)
    @Override
    public void deleteProduct(String productId) {
    	Product extProduct = productMongoRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product doesn't exist with given ID"));
//    	Object object = template.opsForValue().get("product_productDTO::670df9c50105ed025344ebb2");
//    	Boolean delete = template.delete("product_productDTO::670df9c50105ed025344ebb2");
//    	System.out.println(delete);
//    	System.out.println(object.toString());
    	
//    	System.out.println(template.opsForValue().get(productId, 5, 5));
    	
    	
    	productMongoRepository.delete(extProduct);

    }

    @Cacheable(key = "#productId",value = "productDTO")
    @Override
    public ProductDto getProductById(String productId) {
    	System.out.println("FromDataBAse");
    	Product product = productMongoRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product doesn't exist with given ID"));
    	System.out.println("FromDataBAse");
    	ProductDto productDto = new ProductDto();
    	BeanUtils.copyProperties(product, productDto);
        return productDto;
    }
    
//    private ProductDto getDataFromRedis(String productId) {
//    	return (ProductDto)template.opsForValue().get("productDTO");
//    }

    @Override
    public Iterable<ProductDto> getAllProducts() {
        return null;
    }
}
