package com.sqadfour.storereview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqadfour.storereview.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{


	public List<Product> findByProductNameContainsOrderByFinalProductRatingDesc(String productName);

	Product findByProductId(Integer productId);


}
