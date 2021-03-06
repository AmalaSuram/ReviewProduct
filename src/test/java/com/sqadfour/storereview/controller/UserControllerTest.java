package com.sqadfour.storereview.controller;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import java.text.ParseException;


import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import com.sqadfour.storereview.dto.OrdersDto;
import com.sqadfour.storereview.dto.OrdersResponceDto;
import com.sqadfour.storereview.entity.User;
import com.sqadfour.storereview.service.UserService;
import com.sqadfour.storereview.constant.AppConstant;
import com.sqadfour.storereview.dto.OrderRequestDto;
import com.sqadfour.storereview.dto.RatingRequestDto;
import com.sqadfour.storereview.dto.ResponseDto;
import com.sqadfour.storereview.service.UserService;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	@InjectMocks
	UserController userController;
	
	@Mock
	UserService userService;

	User user=new User();
	List<OrdersDto>  orderDtolist=new ArrayList<OrdersDto>();
	OrdersDto ordersDto=new OrdersDto();
	@Before
	public void init() {
		user.setUserId(1);
		ordersDto.setProductName("shampoo");
		orderDtolist.add(ordersDto);
	}
	
	@Test
	public void getOrdersTest() {
		
		when(userService.getOrders(1)).thenReturn(orderDtolist);
		ResponseEntity<OrdersResponceDto> orders = userController.getOrders(1);
		assertEquals(200, orders.getStatusCodeValue());
		
	}

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
		ratingRequestDto.setRatingValue(3);;
		ratingRequestDto.setReview(AppConstant.EXCELLENT_PRODUCT);
		ratingRequestDto.setStoreId(1);
		ResponseDto responseDto = new ResponseDto();
		Mockito.when(userService.giveRating(ratingRequestDto, 1)).thenReturn(responseDto);
		responseDto = userController.giveRating(ratingRequestDto, 1);
		Assert.assertNotNull(responseDto);
	}

}
