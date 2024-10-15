package com.in2it.rediscachingdemo.exception;

public class ProductNotFoundException extends RuntimeException {
	
	public ProductNotFoundException(String msg) {
		super(msg);
	}

}
