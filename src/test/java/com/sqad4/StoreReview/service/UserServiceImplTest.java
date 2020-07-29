package com.sqad4.StoreReview.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import com.sqad4.StoreReview.entity.Orders;
import com.sqad4.StoreReview.entity.OrdersDetails;
import com.sqad4.StoreReview.entity.Product;
import com.sqad4.StoreReview.entity.ProductStoreMapping;
import com.sqad4.StoreReview.entity.Rating;
import com.sqad4.StoreReview.entity.Store;
import com.sqad4.StoreReview.entity.User;
import com.sqad4.StoreReview.repository.OrdersDetailsRepository;
import com.sqad4.StoreReview.repository.OrdersRepository;
import com.sqad4.StoreReview.repository.ProductRepository;
import com.sqad4.StoreReview.repository.ProductStoreMappingRepository;
import com.sqad4.StoreReview.repository.RatingRepository;
import com.sqad4.StoreReview.repository.StoreRepository;
import com.sqad4.StoreReview.repository.UserRepository;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
	
	@InjectMocks
	UserServiceImpl userServiceImpl;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	ProductRepository productRepository;
	
	@Mock
	OrdersRepository orderRepository;
	
	@Mock
	ProductStoreMappingRepository productStoreMappingRepository;
	
	@Mock
	OrdersDetailsRepository ordersDetailsRepository;
	
	@Mock
	RatingRepository ratingRepository;
	
	@Mock
	StoreRepository storeRepository;
	
	@Test
	public void placeOrder() throws ParseException {
		List<OrderRequestDto> orderRequestDtos = new ArrayList<>();
		OrderRequestDto orderRequestDto = new OrderRequestDto();
		orderRequestDto.setProductId(1);
		orderRequestDto.setQuantity(1);
		orderRequestDto.setStoreId(1);
		orderRequestDtos.add(orderRequestDto);
		Integer userId = 1;	
		ResponseDto responseDto = null;
		User users = new User();
		users.setUserId(1);
		users.setPhoneNumber(944493l);
		List<Double> totalPrices = new ArrayList<>();
		Product product = new Product();
		product.setProductDescription(AppConstant.EXCELLENT_PRODUCT);
		product.setProductId(1);
		product.setProductName(AppConstant.PRODUCT_NAME);
		product.setProductPrice(30.0);
		Orders ordered = new Orders();
		ordered.setOrderId(1);
		ordered.setTotalPrice(15.0);
		ordered.setUserId(1);
		ordered.setOrderDate(new Date());
		ProductStoreMapping productStoreMapping = new ProductStoreMapping();	
		productStoreMapping.setProductId(1);
		productStoreMapping.setProductStoreMappingId(1);
		productStoreMapping.setStoreId(1);
		OrdersDetails orderDetails = new OrdersDetails();
		orderDetails.setOrderDetailsId(1);
		orderDetails.setOrderId(1);
		orderDetails.setProductId(1);
		orderDetails.setQuantity(1);
		orderDetails.setStoreId(1);
		
		Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(users));
		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(product));
		Mockito.when(orderRepository.save(Mockito.any(Orders.class))).thenReturn(ordered);
		Mockito.when(productStoreMappingRepository.findByProductIdAndStoreId(Mockito.anyInt(),Mockito.anyInt())).thenReturn(productStoreMapping);
		Mockito.when(ordersDetailsRepository.save(Mockito.any(OrdersDetails.class))).thenReturn(orderDetails);
		responseDto = userServiceImpl.placeOrder(orderRequestDtos, userId);
		Assert.assertNotNull(responseDto);
	}
	
	@Test
	public void giveRating() {
		Integer userId = 1;
		RatingRequestDto ratingRequestDto = new RatingRequestDto();
		ratingRequestDto.setProductId(1);
		ratingRequestDto.setRating(3);
		ratingRequestDto.setReview(AppConstant.EXCELLENT_PRODUCT);
		ratingRequestDto.setStoreId(1);
		Rating savedRating = new Rating();
		savedRating.setProductId(1);
		savedRating.setRating(3);
		savedRating.setReview(AppConstant.EXCELLENT_PRODUCT);
		savedRating.setStoreId(1);
		Float finalrating= null;
		List<Integer> orderIds = new ArrayList<>();
		orderIds.add(1);
		ResponseDto responseDto = new ResponseDto();
		List<Orders> orders = new ArrayList<>();
		Orders order = new Orders();
		order.setOrderDate(new Date());
		order.setOrderId(1);
		order.setTotalPrice(60.0);
		order.setUserId(1);
		orders.add(order);
		OrdersDetails ordersDetails = new OrdersDetails();
		ordersDetails.setOrderDetailsId(1);
		ordersDetails.setOrderId(1);
		ordersDetails.setProductId(1);
		ordersDetails.setQuantity(1);
		ordersDetails.setStoreId(1);
		List<OrdersDetails> ordersDetail = new ArrayList<>();
		ordersDetail.add(ordersDetails);
		Product product = new Product();
		product.setProductDescription(AppConstant.EXCELLENT_PRODUCT);
		product.setProductId(1);
		product.setProductName(AppConstant.PRODUCT_NAME);
		product.setProductPrice(30.0);
		Store store = new Store();
		store.setStoreDescription(AppConstant.EXCELLENT_PRODUCT);
		store.setStoreId(1);
		store.setStoreLocation(AppConstant.LOCATION);
		store.setStoreName(AppConstant.STORE_NAME);
		
		
		Mockito.when(orderRepository.findByUserId(Mockito.anyInt())).thenReturn(orders);
		Mockito.when(ordersDetailsRepository.findByOrderIdInAndProductIdAndStoreId(Mockito.anyList(),Mockito.anyInt(),Mockito.anyInt())).thenReturn(ordersDetail);
		Mockito.when(ratingRepository.save(Mockito.any(Rating.class))).thenReturn(savedRating);
		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(product));
		Mockito.when(storeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(store));
		
		
		responseDto = userServiceImpl.giveRating(ratingRequestDto, userId);
		Assert.assertNotNull(responseDto);
	}

}
