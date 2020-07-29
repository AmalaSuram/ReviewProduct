package com.sqad4.StoreReview.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProductStoreMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productStoreMappingId;
	private Integer productId;
	private Integer storeId;
	public Integer getProductStoreMappingId() {
		return productStoreMappingId;
	}
	public void setProductStoreMappingId(Integer productStoreMappingId) {
		this.productStoreMappingId = productStoreMappingId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
}
