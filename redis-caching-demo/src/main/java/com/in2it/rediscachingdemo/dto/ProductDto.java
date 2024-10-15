package com.in2it.rediscachingdemo.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto implements Serializable {
	
	private  String id;
    private String name;
    private String description;
    private double price;

}
