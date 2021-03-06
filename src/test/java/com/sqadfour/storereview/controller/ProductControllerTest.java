package com.sqadfour.storereview.controller;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.sqadfour.storereview.dto.ProductDto;
import com.sqadfour.storereview.dto.ProductSearchDto;
import com.sqadfour.storereview.service.ProductServiceImpl;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductControllerTest {

	@InjectMocks
	ProductController productController;
	
	@Mock
	ProductServiceImpl productServiceImpl;
	
	@Test
	public void testSearchProduct(){
		
		ProductSearchDto productSearchDto = new ProductSearchDto();
		
		List<ProductDto> productDtoList = new ArrayList<ProductDto>();
		ProductDto productDto = new ProductDto();
		productDto.setProductName("Oil");
		productDto.setStoreName("Store1");
		
		productSearchDto.setProductdtoList(productDtoList);
		
		
		Mockito.when(productServiceImpl.searchProduct("Oil")).thenReturn(productSearchDto);
		ResponseEntity<ProductSearchDto> searchProduct = productController.searchProduct("Oil");
		
		Assert.assertNotNull(searchProduct);
		
	}
}
