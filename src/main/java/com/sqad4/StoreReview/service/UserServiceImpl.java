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
import com.sqad4.StoreReview.dto.RatingRequestDto;
import com.sqad4.StoreReview.dto.ResponseDto;
import com.sqad4.StoreReview.entity.Orders;
import com.sqad4.StoreReview.entity.OrdersDetails;
import com.sqad4.StoreReview.entity.Product;
import com.sqad4.StoreReview.entity.ProductStoreMapping;
import com.sqad4.StoreReview.entity.Rating;
import com.sqad4.StoreReview.entity.Store;
import com.sqad4.StoreReview.entity.User;
import com.sqad4.StoreReview.exception.UserNotFoundException;
import com.sqad4.StoreReview.repository.OrdersDetailsRepository;
import com.sqad4.StoreReview.repository.OrdersRepository;
import com.sqad4.StoreReview.repository.ProductRepository;
import com.sqad4.StoreReview.repository.ProductStoreMappingRepository;
import com.sqad4.StoreReview.repository.RatingRepository;
import com.sqad4.StoreReview.repository.StoreRepository;
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
		Float finalrating= null;
		List<Integer> orderIds = new ArrayList<>();
		List<Orders> orders = orderRepository.findByUserId(userId);
		if (!orders.isEmpty()) {
			orders.stream().forEach(e -> {
				orderIds.add(e.getOrderId());
			});
		} else {
			throw new NullPointerException();
		}
		rating.setRating(ratingRequestDto.getRating());
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
			if(products.isPresent()) {
				Product product = new Product();
				product = products.get();
				product.setFinalProductRating(finalrating);
				productRepository.save(product);
			}
		} 
		if (ratingRequestDto.getStoreId() != 0) {
			finalrating= finalRating(ratingRequestDto.getProductId(), AppConstant.TYPE_STORE);
			Optional<Store> stores = storeRepository.findById(ratingRequestDto.getStoreId());
			if(stores.isPresent()) {
				Store store = new Store();
				store = stores.get();
				store.setFinalProductRating(finalrating);
				storeRepository.save(store);
			}
		}
		return utilityService.responseDto(AppConstant.RATING_SUCCESS + "with Rating Id:" + savedRating.getRatingId());
	}

	public Float finalRating(Integer id, String type) {
		List<Rating> ratings = new ArrayList<>();
		List<Integer> ratingSum = new ArrayList<>();
		Float avgRating = null;
		if (type.equalsIgnoreCase(AppConstant.TYPE_PRODUCT)) {
			ratings = ratingRepository.findByProductId(id);
		} else {
			ratings = ratingRepository.findByStoreId(id);
		}
		if (!ratings.isEmpty()) {
			ratings.forEach(e -> {
				ratingSum.add(e.getRating());
			});
			Optional<Integer> totalrating = ratingSum.stream().reduce((a, b) -> a + b);
			Integer totalSum = totalrating.get();
			Integer size= ratings.size();
			avgRating = (float) totalSum/size;
		}
		return avgRating;
	}
}
