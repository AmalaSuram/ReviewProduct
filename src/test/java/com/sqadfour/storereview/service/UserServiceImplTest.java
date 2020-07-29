package com.sqadfour.storereview.service;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
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
import junit.framework.Assert;
import com.sqadfour.storereview.dto.OrdersDto;
import com.sqadfour.storereview.entity.Orders;
import com.sqadfour.storereview.entity.OrdersDetails;
import com.sqadfour.storereview.entity.Product;
import com.sqadfour.storereview.entity.User;
import com.sqadfour.storereview.repository.OrdersDetailsRepository;
import com.sqadfour.storereview.repository.OrdersRepository;
import com.sqadfour.storereview.repository.ProductRepository;
import com.sqadfour.storereview.repository.StoreRepository;

import com.sqadfour.storereview.constant.AppConstant;
import com.sqadfour.storereview.dto.OrderRequestDto;
import com.sqadfour.storereview.dto.RatingRequestDto;
import com.sqadfour.storereview.dto.ResponseDto;
import com.sqadfour.storereview.entity.Orders;
import com.sqadfour.storereview.entity.OrdersDetails;
import com.sqadfour.storereview.entity.Product;
import com.sqadfour.storereview.entity.ProductStoreMapping;
import com.sqadfour.storereview.entity.Rating;
import com.sqadfour.storereview.entity.Store;
import com.sqadfour.storereview.entity.User;
import com.sqadfour.storereview.repository.OrdersDetailsRepository;
import com.sqadfour.storereview.repository.OrdersRepository;
import com.sqadfour.storereview.repository.ProductRepository;
import com.sqadfour.storereview.repository.ProductStoreMappingRepository;
import com.sqadfour.storereview.repository.RatingRepository;
import com.sqadfour.storereview.repository.StoreRepository;
import com.sqadfour.storereview.repository.UserRepository;
import com.sqadfour.storereview.service.UserServiceImpl;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Mock
	OrdersRepository ordersRepository;

	@Mock
	OrdersDetailsRepository ordersDetailsRepository;
	@Mock
	ProductRepository ProductRepository;
	@Mock
	StoreRepository storeRepository;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	ProductRepository productRepository;
	
	@Mock
	OrdersRepository orderRepository;
	
	@Mock
	ProductStoreMappingRepository productStoreMappingRepository;
	
	@Mock
	RatingRepository ratingRepository;
	List<Orders> orders = new ArrayList<Orders>();

	Orders orderslist = new Orders();

	User user = new User();
	List<OrdersDetails> ordersDetailslist = new ArrayList<OrdersDetails>();
	OrdersDetails ordersDetails = new OrdersDetails();

	Product product = new Product();

	@Before
	public void init() {
		orderslist.setOrderId(1);
		orderslist.setUserId(1);
		orderslist.setTotalPrice(100.0);
		orderslist.setOrderDate(new Date());
		orders.add(orderslist);

		user.setUserId(1);
		ordersDetails.setOrderDetailsId(1);
		ordersDetails.setProductId(1);
		ordersDetails.setOrderId(1);
		ordersDetails.setQuantity(1);
		ordersDetails.setStoreId(1);
		ordersDetailslist.add(ordersDetails);

		product.setProductId(1);
		product.setProductDescription("good");
		product.setProductPrice(100.0);
		product.setFinalProductRating((float) 4);
		product.setProductName("redmi");

	}
	
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
