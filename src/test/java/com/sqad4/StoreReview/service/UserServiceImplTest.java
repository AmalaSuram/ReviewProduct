package com.sqad4.StoreReview.service;

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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import junit.framework.Assert;
import com.sqad4.StoreReview.dto.OrdersDto;
import com.sqad4.StoreReview.entity.Orders;
import com.sqad4.StoreReview.entity.OrdersDetails;
import com.sqad4.StoreReview.entity.Product;
import com.sqad4.StoreReview.entity.User;
import com.sqad4.StoreReview.repository.OrdersDetailsRepository;
import com.sqad4.StoreReview.repository.OrdersRepository;
import com.sqad4.StoreReview.repository.ProductRepository;
import com.sqad4.StoreReview.repository.StoreRepository;

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
    @Ignore
	@Test
	public void getOrders() {

		when(ordersRepository.findAllByUserId(1)).thenReturn(orders);
		when(ordersDetailsRepository.findByOrderId(1)).thenReturn(ordersDetailslist);
		when(ProductRepository.findByProductId(1)).thenReturn(product);
		List<OrdersDto> response = userServiceImpl.getOrders(1);

		Assert.assertNotNull(response);

	}

}
