package com.sqadfour.storereview.service;

import com.sqadfour.storereview.dto.ProductSearchDto;

public interface ProductService {

	public  ProductSearchDto searchProduct(String productName);
}
