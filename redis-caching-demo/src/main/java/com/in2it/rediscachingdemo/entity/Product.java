package com.in2it.rediscachingdemo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Entity
//@Table(name = "products")
public class Product {
    @Id
    private  String id;
    private String name;
    private String description;
    private double price;
}