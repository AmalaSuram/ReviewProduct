package com.sqadfour.storereview.service;


import java.text.ParseException;
import java.util.List;

import com.sqadfour.storereview.dto.OrdersDto;
import com.sqadfour.storereview.dto.OrderRequestDto;
import com.sqadfour.storereview.dto.RatingRequestDto;
import com.sqadfour.storereview.dto.ResponseDto;

public interface UserService {

	ResponseDto placeOrder(List<OrderRequestDto> orderRequestDtos, Integer userId) throws ParseException;

	ResponseDto giveRating(RatingRequestDto ratingRequestDto, Integer userId);
	List<OrdersDto> getOrders(Integer userId);

}

