package com.sqad4.StoreReview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqad4.StoreReview.entity.OrdersDetails;

@Repository
public interface OrdersDetailsRepository extends JpaRepository<OrdersDetails, Integer>{

	List<OrdersDetails> findByOrderIdInAndProductIdAndStoreId(List<Integer> orderIds, Integer productId,
			Integer storeId);

	List<OrdersDetails> findByOrderIdInAndStoreId(List<Integer> orderIds, Integer storeId);

	List<OrdersDetails> findByOrderIdInAndProductId(List<Integer> orderIds, Integer productId);

}
