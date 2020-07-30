package com.sqadfour.storereview.dto;

import java.util.List;

public class OrdersResponceDto {
	private Integer statusCode;
	private String message;
	private List<OrdersDto> ordersDto;
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
		return ordersDto;
	}
	public void setOrdersDto(List<OrdersDto> ordersDto) {
		this.ordersDto = ordersDto;
	}
}
