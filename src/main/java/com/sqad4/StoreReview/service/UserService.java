package com.sqad4.StoreReview.service;

import java.util.List;

import com.sqad4.StoreReview.dto.OrdersDto;

public interface UserService {

	List<OrdersDto> getOrders(Integer userId);

}
