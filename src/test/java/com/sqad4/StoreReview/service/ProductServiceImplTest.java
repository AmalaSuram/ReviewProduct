package com.sqad4.StoreReview.service;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.sqad4.StoreReview.dto.ProductDto;
import com.sqad4.StoreReview.dto.ProductSearchDto;
import com.sqad4.StoreReview.entity.Product;
import com.sqad4.StoreReview.entity.ProductStoreMapping;
import com.sqad4.StoreReview.repository.ProductRepository;
import com.sqad4.StoreReview.repository.ProductStoreMappingRepository;
import com.sqad4.StoreReview.repository.StoreRepository;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductServiceImplTest {
	
	@InjectMocks
	ProductServiceImpl productServiceImpl ;

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
	public void testsearchProduct(){
		
		ProductSearchDto productSearchDto = new ProductSearchDto();
		List<Product> productList = new ArrayList<>();
		Product product = new Product();
		product.setProductId(1);
		product.setProductName("Oil");
		productList.add(product);
		
		List<ProductStoreMapping> StoreList = new ArrayList<>();
		ProductStoreMapping productStoreMapping = new ProductStoreMapping();
		productStoreMapping.setProductId(1);
		productStoreMapping.setStoreId(1);
		
		List<ProductDto> productDtoList = new ArrayList<ProductDto>();
		ProductDto productDto = new ProductDto();
		productDto.setProductName("Oil");
		productDto.setStoreName("Store1");
		
		productSearchDto.setProductdtoList(productDtoList);
		
		Mockito.when(productRepository.findByProductNameContainsOrderByFinalProductRatingDesc("Oil")).thenReturn(productList);
		
		Mockito.when(productStoreMappingRepository.findByProductId(1)).thenReturn(StoreList);
		
		productSearchDto = productServiceImpl.searchProduct("Oil");
		
		Assert.assertNotNull(productSearchDto);
		
	}
}
