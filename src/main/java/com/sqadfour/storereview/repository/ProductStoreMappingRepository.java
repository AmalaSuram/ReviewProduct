package com.sqadfour.storereview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqadfour.storereview.entity.ProductStoreMapping;

@Repository
public interface ProductStoreMappingRepository extends JpaRepository<ProductStoreMapping, Integer>{


	public ProductStoreMapping findByProductIdAndStoreId(Integer productId, Integer storeId);
	
	
	public List<ProductStoreMapping> findByProductId (Integer productId);
}
