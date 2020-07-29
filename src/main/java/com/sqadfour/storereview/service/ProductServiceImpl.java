package com.sqadfour.storereview.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqadfour.storereview.constant.AppConstant;
import com.sqadfour.storereview.dto.ProductDto;
import com.sqadfour.storereview.dto.ProductSearchDto;
import com.sqadfour.storereview.dto.ResponseDto;
import com.sqadfour.storereview.entity.Product;
import com.sqadfour.storereview.entity.ProductStoreMapping;
import com.sqadfour.storereview.entity.Store;
import com.sqadfour.storereview.exception.ProductNotFoundException;
import com.sqadfour.storereview.repository.ProductRepository;
import com.sqadfour.storereview.repository.ProductStoreMappingRepository;
import com.sqadfour.storereview.repository.StoreRepository;

/**
 * this class is use for serching product
 * @author shraddha
 *
 */
@Service
public class ProductServiceImpl implements ProductService{

	private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
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
					
					Optional<Store> store = storeRepository.findById(productStoreMapping.getStoreId());
					logger.info("Store Name: "+store.get().getStoreName());
					
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
