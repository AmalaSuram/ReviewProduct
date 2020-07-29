package com.sqad4.StoreReview.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.sqad4.StoreReview.constant.AppConstant;
import com.sqad4.StoreReview.dto.OrderRequestDto;
import com.sqad4.StoreReview.dto.RatingRequestDto;
import com.sqad4.StoreReview.dto.ResponseDto;
import com.sqad4.StoreReview.service.UserService;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	@InjectMocks
	UserController userController;
	
	@Mock
	UserService userService;

	@Test
	public void placeOrder() throws ParseException {
		Integer userId = 1;
		List<OrderRequestDto> orderRequestDtos = new ArrayList<>();
		OrderRequestDto orderRequestDto = new OrderRequestDto();
		orderRequestDto.setProductId(1);
		orderRequestDto.setQuantity(1);
		orderRequestDto.setStoreId(1);
		orderRequestDtos.add(orderRequestDto);
		ResponseDto responseDto = new ResponseDto();
		Mockito.when(userService.placeOrder(orderRequestDtos, 1)).thenReturn(responseDto);
		responseDto = userController.placeOrder(orderRequestDtos, 1);
		Assert.assertNotNull(responseDto);
	}
	
	@Test
	public void giveRating() throws ParseException {
		Integer userId = 1;
		RatingRequestDto ratingRequestDto = new RatingRequestDto();
		ratingRequestDto.setProductId(1);
		ratingRequestDto.setRating(3);
		ratingRequestDto.setReview(AppConstant.EXCELLENT_PRODUCT);
		ratingRequestDto.setStoreId(1);
		ResponseDto responseDto = new ResponseDto();
		Mockito.when(userService.giveRating(ratingRequestDto, 1)).thenReturn(responseDto);
		responseDto = userController.giveRating(ratingRequestDto, 1);
		Assert.assertNotNull(responseDto);
	}

}
