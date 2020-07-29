package com.sqad4.StoreReview.service;


import java.text.ParseException;
import java.util.List;

import com.sqad4.StoreReview.dto.OrdersDto;
import com.sqad4.StoreReview.dto.OrderRequestDto;
import com.sqad4.StoreReview.dto.RatingRequestDto;
import com.sqad4.StoreReview.dto.ResponseDto;

public interface UserService {

	ResponseDto placeOrder(List<OrderRequestDto> orderRequestDtos, Integer userId) throws ParseException;

	ResponseDto giveRating(RatingRequestDto ratingRequestDto, Integer userId);
	List<OrdersDto> getOrders(Integer userId);

}

