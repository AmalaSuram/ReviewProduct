package com.sqad4.StoreReview.dto;

import java.util.List;

public class ProductSearchDto {

	
	private List<ProductDto> productdtoList;
	private ResponseDto responseDto;
	
	
	
	public List<ProductDto> getProductdtoList() {
		return productdtoList;
	}
	public void setProductdtoList(List<ProductDto> productdtoList) {
		this.productdtoList = productdtoList;
	}
	public ResponseDto getResponseDto() {
		return responseDto;
	}
	public void setResponseDto(ResponseDto responseDto) {
		this.responseDto = responseDto;
	}
	
	
	
}
