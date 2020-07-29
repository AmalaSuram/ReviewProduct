package com.sqad4.StoreReview.exception;
/**
 * 
 * @author shraddha
 *
 */
public class ProductNotFoundException extends RuntimeException{

	public ProductNotFoundException(String productName) {
		super(String.format("User with Id %d not found", productName));
	}
}
