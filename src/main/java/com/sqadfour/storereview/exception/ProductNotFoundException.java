package com.sqadfour.storereview.exception;
/**
 * 
 * @author shraddha
 *
 */
public class ProductNotFoundException extends RuntimeException{

	public ProductNotFoundException(String productName) {
		super(String.format("Product "+productName+" not found"));
	}
}
