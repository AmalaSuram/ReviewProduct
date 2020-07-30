package com.sqadfour.storereview.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.sqadfour.storereview.constant.AppConstant;
import com.sqadfour.storereview.dto.ProductDto;
import com.sqadfour.storereview.dto.ProductSearchDto;
import com.sqadfour.storereview.dto.ResponseDto;
import com.sqadfour.storereview.entity.Product;
import com.sqadfour.storereview.entity.ProductStoreMapping;
import com.sqadfour.storereview.repository.ProductRepository;
import com.sqadfour.storereview.repository.ProductStoreMappingRepository;
import com.sqadfour.storereview.repository.StoreRepository;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductServiceImplTest {

	@InjectMocks
	ProductServiceImpl productServiceImpl;

	@Mock
	ProductRepository productRepository;

	@Mock
	ProductStoreMappingRepository productStoreMappingRepository;

	@Mock
	StoreRepository storeRepository;

	/**
	 * 
	 * Test case
	 */

	@Test
	public void testsearchProduct() {

		ProductSearchDto productSearchDto = new ProductSearchDto();
		ResponseDto responseDto = new ResponseDto();

		List<Product> productList = new ArrayList<>();
		Product product = new Product();
		product.setProductId(1);
		product.setProductName("Oil");
		productList.add(product);

		List<ProductStoreMapping> StoreList = new ArrayList<>();
		ProductStoreMapping productStoreMapping = new ProductStoreMapping();
		productStoreMapping.setProductId(1);
		productStoreMapping.setStoreId(1);
		StoreList.add(productStoreMapping);

		List<ProductDto> productDtoList = new ArrayList<ProductDto>();
		ProductDto productDto = new ProductDto();
		productDto.setProductName("Oil");
		productDto.setStoreName("Store1");

		productSearchDto.setProductdtoList(productDtoList);

		responseDto.setResponseMessage(AppConstant.PRODUCT_SEARCH_SUCCESS);
		responseDto.setStatusCode(200);
		responseDto.setStatusMessage(AppConstant.USER_FOUND_PRODUCTS);
		productSearchDto.setResponseDto(responseDto);

		Mockito.when(productRepository.findByProductNameContainsOrderByFinalProductRatingDesc("Oil"))
				.thenReturn(productList);

		Mockito.when(productStoreMappingRepository.findByProductId(1)).thenReturn(StoreList);

		productSearchDto = productServiceImpl.searchProduct("Oil");

		Assert.assertNotNull(productSearchDto);

	}
}
