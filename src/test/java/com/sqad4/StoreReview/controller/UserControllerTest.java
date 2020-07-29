package com.sqad4.StoreReview.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.sqad4.StoreReview.dto.OrdersDto;
import com.sqad4.StoreReview.dto.OrdersResponceDto;
import com.sqad4.StoreReview.entity.User;
import com.sqad4.StoreReview.service.UserService;
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

}
