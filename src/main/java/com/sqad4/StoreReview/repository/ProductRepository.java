package com.sqad4.StoreReview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqad4.StoreReview.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	public List<Product> findByProductNameContainsOrderByFinalProductRatingDesc(String productName);
}
