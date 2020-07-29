package com.sqad4.StoreReview.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.util.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqad4.StoreReview.constant.AppConstant;
import com.sqad4.StoreReview.controller.UserController;
import com.sqad4.StoreReview.dto.OrderRequestDto;
import com.sqad4.StoreReview.dto.ResponseDto;
import com.sqad4.StoreReview.entity.Orders;
import com.sqad4.StoreReview.entity.OrdersDetails;
import com.sqad4.StoreReview.entity.Product;
import com.sqad4.StoreReview.entity.ProductStoreMapping;
import com.sqad4.StoreReview.entity.User;
import com.sqad4.StoreReview.exception.UserNotFoundException;
import com.sqad4.StoreReview.repository.OrdersDetailsRepository;
import com.sqad4.StoreReview.repository.OrdersRepository;
import com.sqad4.StoreReview.repository.ProductRepository;
import com.sqad4.StoreReview.repository.ProductStoreMappingRepository;
import com.sqad4.StoreReview.repository.UserRepository;
import com.sqad4.StoreReview.utility.UtilityService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	OrdersRepository orderRepository;

	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductStoreMappingRepository productStoreMappingRepository;
	@Autowired
	OrdersDetailsRepository ordersDetailsRepository;

	UtilityService utilityService = new UtilityService();
	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * @author sweta
	 * @throws ParseException
	 * @since 24-07-2020 This method is used for placing order and then doing
	 *        transactions
	 */
	@Transactional
	@Override
	public ResponseDto placeOrder(List<OrderRequestDto> orderRequestDtos, Integer userId) throws ParseException {
		
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		List<Double> totalPrices = new ArrayList<>();
		orderRequestDtos.stream().forEach(e->{
			Optional<Product> product = productRepository.findById(e.getProductId());
			if(product.isPresent()) {
			 Double itemPrice = e.getQuantity() * product.get().getProductPrice();
			 totalPrices.add(itemPrice);
			// totalOrderprice =  totalOrderprice + itemPrice;
			}
		});
		Optional<Double> totalOrderpriceDouble= totalPrices.stream().reduce((a,b)->a+b);
		Orders order = new Orders();
		order.setOrderDate(new Date());
		order.setUserId(user.getUserId());
		if(totalOrderpriceDouble.isPresent()) {
		order.setTotalPrice(totalOrderpriceDouble.get());
		}else {
			throw new NullPointerException();
		}
		Orders ordered = orderRepository.save(order);
		logger.info(AppConstant.ORDER_SAVED_IN_DATABASE);
		
		if (!orderRequestDtos.isEmpty() && orderRequestDtos.get(0).getProductId() != 0) {
			orderRequestDtos.stream().forEach(e->{
				ProductStoreMapping productStoreMapping = productStoreMappingRepository
						.findByProductIdAndStoreId(e.getProductId(), e.getStoreId());
				if (!ObjectUtils.isEmpty(productStoreMapping)) {
					OrdersDetails orderDetails = new OrdersDetails();
					orderDetails.setOrderId(ordered.getOrderId());
					orderDetails.setProductId(e.getProductId());
					orderDetails.setStoreId(e.getStoreId());
					orderDetails.setQuantity(e.getQuantity());
					ordersDetailsRepository.save(orderDetails);
				} else {
					throw new NullPointerException();
				}
			});
			logger.info("All OrderDetails get saved in OrderDetails Table");
		}
		return utilityService.responseDto(AppConstant.ORDER_SUCCESS);
	}

}
