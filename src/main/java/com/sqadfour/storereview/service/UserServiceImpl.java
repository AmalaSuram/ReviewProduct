package com.sqadfour.storereview.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import java.time.LocalDate;
import org.springframework.util.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqadfour.storereview.constant.AppConstant;
import com.sqadfour.storereview.controller.UserController;
import com.sqadfour.storereview.dto.OrderRequestDto;
import com.sqadfour.storereview.dto.OrdersDto;
import com.sqadfour.storereview.dto.RatingRequestDto;
import com.sqadfour.storereview.dto.ResponseDto;
import com.sqadfour.storereview.entity.Orders;
import com.sqadfour.storereview.entity.OrdersDetails;
import com.sqadfour.storereview.entity.Product;
import com.sqadfour.storereview.entity.ProductStoreMapping;
import com.sqadfour.storereview.entity.Rating;
import com.sqadfour.storereview.entity.Store;
import com.sqadfour.storereview.entity.User;
import com.sqadfour.storereview.exception.UserNotFoundException;
import com.sqadfour.storereview.repository.OrdersDetailsRepository;
import com.sqadfour.storereview.repository.OrdersRepository;
import com.sqadfour.storereview.repository.ProductRepository;
import com.sqadfour.storereview.repository.ProductStoreMappingRepository;
import com.sqadfour.storereview.repository.RatingRepository;
import com.sqadfour.storereview.repository.StoreRepository;
import com.sqadfour.storereview.repository.UserRepository;
import com.sqadfour.storereview.utility.UtilityService;

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
	@Autowired
	RatingRepository ratingRepository;
	@Autowired
	StoreRepository storeRepository;

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
		orderRequestDtos.stream().forEach(e -> {
			Optional<Product> product = productRepository.findById(e.getProductId());
			if (product.isPresent()) {
				Double itemPrice = e.getQuantity() * product.get().getProductPrice();
				totalPrices.add(itemPrice);
			}
		});
		Optional<Double> totalOrderpriceDouble = totalPrices.stream().reduce((a, b) -> a + b);
		Orders order = new Orders();
		order.setOrderDate(new Date());
		order.setUserId(user.getUserId());
		if (totalOrderpriceDouble.isPresent()) {
			order.setTotalPrice(totalOrderpriceDouble.get());
		} else {
			throw new NullPointerException();
		}
		Orders ordered = orderRepository.save(order);
		logger.info(AppConstant.ORDER_SAVED_IN_DATABASE);

		if (!orderRequestDtos.isEmpty() && orderRequestDtos.get(0).getProductId() != 0) {
			orderRequestDtos.stream().forEach(e -> {
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
			logger.info(AppConstant.ORDER_DETAILS_SAVED);
		}
		return utilityService.responseDto(AppConstant.ORDER_SUCCESS + "with Order Id:" + ordered.getOrderId());
	}
	
	/**
	 * @author sweta
	 * @since 29-07-2020
	 * This methos is used to store users rating and update the final rating of product and Store
	 */

	@Transactional
	@Override
	public ResponseDto giveRating(RatingRequestDto ratingRequestDto, Integer userId) {
		logger.info("In giveRating in User ServiceImpl");
		Rating rating = new Rating();
		Float finalrating = null;
		List<Integer> orderIds = new ArrayList<>();
		List<Orders> orders = orderRepository.findByUserId(userId);
		if (!orders.isEmpty()) {
			orders.stream().forEach(e -> {
				orderIds.add(e.getOrderId());
			});
		} else {
			throw new NullPointerException();
		}
		rating.setRatingValue(ratingRequestDto.getRatingValue());
		rating.setReview(ratingRequestDto.getReview());
		rating.setUserId(userId);
		if (ratingRequestDto.getProductId() != 0 && ratingRequestDto.getStoreId() != 0) {
			List<OrdersDetails> ordersDetails = ordersDetailsRepository.findByOrderIdInAndProductIdAndStoreId(orderIds,
					ratingRequestDto.getProductId(), ratingRequestDto.getStoreId());
			if (ordersDetails.isEmpty()) {
				throw new NullPointerException();
			}
			rating.setProductId(ratingRequestDto.getProductId());
			rating.setStoreId(ratingRequestDto.getStoreId());
		} else if (ratingRequestDto.getProductId() == 0 && ratingRequestDto.getStoreId() != 0) {
			List<OrdersDetails> ordersDetails = ordersDetailsRepository.findByOrderIdInAndStoreId(orderIds,
					ratingRequestDto.getStoreId());
			if (ordersDetails.isEmpty()) {
				throw new NullPointerException();
			}
			rating.setStoreId(ratingRequestDto.getStoreId());
		} else if (ratingRequestDto.getProductId() != 0 && ratingRequestDto.getStoreId() == 0) {
			List<OrdersDetails> ordersDetails = ordersDetailsRepository.findByOrderIdInAndProductId(orderIds,
					ratingRequestDto.getProductId());
			if (ordersDetails.isEmpty()) {
				throw new NullPointerException();
			}
			rating.setProductId(ratingRequestDto.getProductId());
		} else {
			throw new NullPointerException();
		}
		Rating savedRating = ratingRepository.save(rating);
		logger.info(AppConstant.RATING_SUCCESS);
		if (ratingRequestDto.getProductId() != 0) {
			finalrating = finalRating(ratingRequestDto.getProductId(), AppConstant.TYPE_PRODUCT);
			Optional<Product> products = productRepository.findById(ratingRequestDto.getProductId());
			if (products.isPresent()) {
				Product product = products.get();
				product.setFinalProductRating(finalrating);
				productRepository.save(product);
			}
		}
		if (ratingRequestDto.getStoreId() != 0) {
			finalrating= finalRating(ratingRequestDto.getProductId(), AppConstant.TYPE_STORE);
			Optional<Store> stores = storeRepository.findById(ratingRequestDto.getStoreId());
			if (stores.isPresent()) {
				Store store = stores.get();
				store.setFinalProductRating(finalrating);
				storeRepository.save(store);
			}
		}
		return utilityService.responseDto(AppConstant.RATING_SUCCESS + "with Rating Id:" + savedRating.getRatingId());
	}

	@Override
	public List<OrdersDto> getOrders(Integer userId) {

		List<OrdersDto> orderDtolist = new ArrayList<>();

		List<Orders> orders = orderRepository.findAllByUserId(userId);

		for (Orders ordersEntity : orders) {

			List<OrdersDetails> orderDetails = ordersDetailsRepository.findByOrderId(ordersEntity.getOrderId());
			for (OrdersDetails ordersDetailEntity : orderDetails) {

				Product product = productRepository.findByProductId(ordersDetailEntity.getProductId());
				Store store = storeRepository.findByStoreId(ordersDetailEntity.getStoreId());
				OrdersDto ordersDto = new OrdersDto();
				ordersDto.setOrderDate(ordersEntity.getOrderDate());
				ordersDto.setProductName(product.getProductName());
				ordersDto.setStoreName(store.getStoreName());
				ordersDto.setQuantity(ordersDetailEntity.getQuantity());
				ordersDto.setTotalPrice(product.getProductPrice());
				orderDtolist.add(ordersDto);

			}

		}

		return orderDtolist;

	}

	public Float finalRating(Integer id, String type) {
		List<Rating> ratings = null;
		List<Integer> ratingSum = new ArrayList<>();
		Integer totalSum = 0;
		Float avgRating = null;
		if (type.equalsIgnoreCase(AppConstant.TYPE_PRODUCT)) {
			ratings = ratingRepository.findByProductId(id);
		} else {
			ratings = ratingRepository.findByStoreId(id);
		}
		if (!ratings.isEmpty()) {
			ratings.forEach(e -> {
				ratingSum.add(e.getRatingValue());
			});
			Optional<Integer> totalrating = ratingSum.stream().reduce((a, b) -> a + b);
			if(totalrating.isPresent()) {
			 totalSum = totalrating.get();
			}
			Integer size= ratings.size();
			avgRating = (float) totalSum/size;

		}
		return avgRating;
	}
}
