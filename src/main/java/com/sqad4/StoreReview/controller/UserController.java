package com.sqad4.StoreReview.controller;

import java.util.List;

import org.apache.commons.httpclient.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * @author sweta
 *@since 29-07-2020
 *This class is to handle user request
 */

import com.sqad4.StoreReview.constant.AppConstant;
import com.sqad4.StoreReview.dto.OrdersDto;
import com.sqad4.StoreReview.dto.OrdersResponceDto;
import com.sqad4.StoreReview.exception.UserNotFoundException;
import com.sqad4.StoreReview.service.UserService;
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<OrdersResponceDto> getOrders(@PathVariable Integer userId ){
		List<OrdersDto> orders = userService.getOrders(userId);
	
		OrdersResponceDto ordersResponceDto=new OrdersResponceDto();
		ordersResponceDto.setOrdersDto(orders);
		ordersResponceDto.setMessage(AppConstant.SUCCESS_STATUS_MESSAGE);
		ordersResponceDto.setStatusCode(AppConstant.SUCCESS_STATUS_CODE);
		return ResponseEntity.ok(ordersResponceDto);
		

}

}