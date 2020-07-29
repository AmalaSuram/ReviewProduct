package com.sqad4.StoreReview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * @author sweta
 *@since 29-07-2020
 *This class is to handle requests related to product
 */

import com.sqad4.StoreReview.dto.ProductSearchDto;
import com.sqad4.StoreReview.service.ProductService;
import com.sqad4.StoreReview.service.ProductServiceImpl;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductServiceImpl productServiceImpl;
	
	@GetMapping("/search")
	public ResponseEntity<ProductSearchDto> searchProduct(@RequestParam String productName){
		
		ProductSearchDto productSearchDto = productServiceImpl.searchProduct(productName);
		
		return new ResponseEntity<ProductSearchDto>(productSearchDto, HttpStatus.OK);
		
	}
	
}
