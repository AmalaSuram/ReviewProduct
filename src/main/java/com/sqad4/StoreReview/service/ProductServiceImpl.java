package com.sqad4.StoreReview.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqad4.StoreReview.constant.AppConstant;
import com.sqad4.StoreReview.dto.ProductDto;
import com.sqad4.StoreReview.dto.ProductSearchDto;
import com.sqad4.StoreReview.dto.ResponseDto;
import com.sqad4.StoreReview.entity.Product;
import com.sqad4.StoreReview.entity.ProductStoreMapping;
import com.sqad4.StoreReview.entity.Store;
import com.sqad4.StoreReview.exception.ProductNotFoundException;
import com.sqad4.StoreReview.repository.ProductRepository;
import com.sqad4.StoreReview.repository.ProductStoreMappingRepository;
import com.sqad4.StoreReview.repository.StoreRepository;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductStoreMappingRepository productStoreMappingRepository;
	
	@Autowired
	StoreRepository storeRepository;
	
	public  ProductSearchDto searchProduct(String productName){
		
		ProductSearchDto productSearchDto = new ProductSearchDto(); 
		ResponseDto responseDto = new ResponseDto();
		List<ProductDto> productDtolist = new ArrayList<>();
		List<Product> ProductList = productRepository.findByProductNameContainsOrderByFinalProductRatingDesc(productName);
		if(!ProductList.isEmpty()){
			for (Product product : ProductList) {
				
				List<ProductStoreMapping> StoreList = productStoreMappingRepository.findByProductId(product.getProductId());
			
				for (ProductStoreMapping productStoreMapping : StoreList) {
					
					ProductDto productDto = new ProductDto();
					BeanUtils.copyProperties(product, productDto);
					
					System.out.println("Store ID "+productStoreMapping.getStoreId());
					Optional<Store> store = storeRepository.findById(productStoreMapping.getStoreId());
					System.out.println(store.get().getStoreName());
					
					
					productDto.setStoreName(store.get().getStoreName());
					productDtolist.add(productDto);	
				}
				
			}
			productSearchDto.setProductdtoList(productDtolist);
			if(!productDtolist.isEmpty()){
				responseDto.setResponseMessage(AppConstant.PRODUCT_SEARCH_SUCCESS);
				responseDto.setStatusCode(200);
				responseDto.setStatusMessage(AppConstant.USER_FOUND_PRODUCTS);
				productSearchDto.setResponseDto(responseDto);
			}
		}else{
			
			throw new ProductNotFoundException(productName);
		}
		return productSearchDto;
	}
	
}
