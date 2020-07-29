package com.sqad4.StoreReview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * @author sweta
 *@since 29-07-2020
 *This class is to handle requests related to product
 */

import com.sqad4.StoreReview.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	ProductService productService;
}
