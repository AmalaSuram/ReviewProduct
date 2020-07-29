package com.sqad4.StoreReview.dto;

public class ProductDto {

	private String productName;
	private String storeName;
	
	private String productDescription;
	private Double productPrice;
	private Float finalProductRating;
	
	
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public Double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}
	public Float getFinalProductRating() {
		return finalProductRating;
	}
	public void setFinalProductRating(Float finalProductRating) {
		this.finalProductRating = finalProductRating;
	}
	
	
}
