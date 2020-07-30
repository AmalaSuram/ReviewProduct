package com.sqadfour.storereview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqadfour.storereview.entity.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer>{

	List<Rating> findByProductId(Integer productId);

	List<Rating> findByStoreId(Integer id);

}
