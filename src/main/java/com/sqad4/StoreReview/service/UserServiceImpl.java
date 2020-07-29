package com.sqad4.StoreReview.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqad4.StoreReview.dto.OrdersDto;
import com.sqad4.StoreReview.entity.Orders;
import com.sqad4.StoreReview.entity.OrdersDetails;
import com.sqad4.StoreReview.entity.Product;
import com.sqad4.StoreReview.entity.Store;
import com.sqad4.StoreReview.repository.OrdersDetailsRepository;
import com.sqad4.StoreReview.repository.OrdersRepository;
import com.sqad4.StoreReview.repository.ProductRepository;
import com.sqad4.StoreReview.repository.StoreRepository;
import com.sqad4.StoreReview.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	OrdersRepository ordersRepository;

	@Autowired
	OrdersDetailsRepository ordersDetailsRepository;
	@Autowired
	ProductRepository ProductRepository;
	@Autowired
	StoreRepository storeRepository;

	@Override
	public List<OrdersDto> getOrders(Integer userId) {

		List<OrdersDto> orderDtolist = new ArrayList<>();

		List<Orders> orders = ordersRepository.findAllByUserId(userId);

		for (Orders ordersEntity : orders) {

			List<OrdersDetails> orderDetails = ordersDetailsRepository.findByOrderId(ordersEntity.getOrderId());
			for (OrdersDetails ordersDetailEntity : orderDetails) {

				Product product = ProductRepository.findByProductId(ordersDetailEntity.getProductId());
				Store store = storeRepository.findByStoreId(ordersDetailEntity.getStoreId());
				OrdersDto ordersDto = new OrdersDto();
				ordersDto.setOrderDate(ordersEntity.getOrderDate());
				ordersDto.setProductName(product.getProductName());
				ordersDto.setStoreName(store.getStoreName());
				ordersDto.setQuantity(ordersDetailEntity.getQuantity());
				ordersDto.setTotalPrice(product.getProductPrice());
				orderDtolist.add(ordersDto);

			}

		}

		return orderDtolist;
	}

}

