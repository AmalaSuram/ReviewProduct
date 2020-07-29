package com.sqad4.StoreReview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqad4.StoreReview.entity.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer>{


	List<Orders> findAllByUserId(Integer userId);

}
