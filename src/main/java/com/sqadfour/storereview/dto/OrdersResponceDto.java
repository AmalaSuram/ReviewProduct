package com.sqadfour.storereview.dto;

import java.util.List;

public class OrdersResponceDto {
	private Integer statusCode;
	private String message;
	private List<OrdersDto> OrdersDto;
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<OrdersDto> getOrdersDto() {
		return OrdersDto;
	}
	public void setOrdersDto(List<OrdersDto> ordersDto) {
		OrdersDto = ordersDto;
	}
	
}
