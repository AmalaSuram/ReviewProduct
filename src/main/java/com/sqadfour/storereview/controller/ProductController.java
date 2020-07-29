package com.sqadfour.storereview.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sqadfour.storereview.constant.AppConstant;
import com.sqadfour.storereview.dto.ProductSearchDto;
import com.sqadfour.storereview.service.ProductService;
import com.sqadfour.storereview.service.ProductServiceImpl;

/**
 * @author Sweta
 *@since 29-07-2020
 *This class is to handle requests related to product
 */

@RestController
@RequestMapping("/products")
public class ProductController {
	
	private static Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductServiceImpl productServiceImpl;
	
	@GetMapping("/search")
	public ResponseEntity<ProductSearchDto> searchProduct(@RequestParam String productName){
		
		ProductSearchDto productSearchDto = productServiceImpl.searchProduct(productName);
		logger.info(AppConstant.PRODUCT_SEARCH_SUCCESS);
		return new ResponseEntity<>(productSearchDto, HttpStatus.OK);
		
	}
	
}
