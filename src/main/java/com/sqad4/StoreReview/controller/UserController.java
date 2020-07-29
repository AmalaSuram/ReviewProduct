package com.sqad4.StoreReview.controller;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqad4.StoreReview.dto.OrderRequestDto;
import com.sqad4.StoreReview.dto.RatingRequestDto;
import com.sqad4.StoreReview.dto.ResponseDto;
import com.sqad4.StoreReview.service.UserService;

/**
 * 
 * @author sweta
 *@since 29-07-2020
 *This class is to handle user request
 */
@RestController
@RequestMapping("/products")
public class UserController {
	@Autowired
	UserService userService;
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	/**
	 * This method is used to place order for the user
	 * 
	 * @param orderRequestDtos this is the list of requestDto which contains
	 *                         quantity,productId and Store Id
	 * @param userId           this is id of user
	 * @return responseDto
	 * @author sweta
	 * @throws ParseException 
	 * @since 24-07-2020
	 */
	@PostMapping("/{userId}")
	public ResponseDto placeOrder(@RequestBody List<OrderRequestDto> orderRequestDtos,@PathVariable Integer userId) throws ParseException {
		logger.info("In userController in placeOrder method");
		ResponseDto responseDto = null;
		if (userId != 0 && !orderRequestDtos.isEmpty()) {
			responseDto = userService.placeOrder(orderRequestDtos, userId);
		}
		return responseDto;
	}
	
	@PostMapping("/rating/{userId}")
	public ResponseDto giveRating(@RequestBody RatingRequestDto ratingRequestDto,@PathVariable Integer userId) throws ParseException {
		logger.info("In userController in giveRating method");
		ResponseDto responseDto = null;
		if (userId != 0 && !ObjectUtils.isEmpty(ratingRequestDto) && ratingRequestDto.getRating() !=0) {
			responseDto = userService.giveRating(ratingRequestDto, userId);
		}else {
			throw new NullPointerException();
		}
		return responseDto;
	}
}
