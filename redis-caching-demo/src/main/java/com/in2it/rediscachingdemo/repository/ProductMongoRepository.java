package com.in2it.rediscachingdemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.in2it.rediscachingdemo.entity.Product;


@Repository
public interface ProductMongoRepository extends MongoRepository<Product, String>{

}
