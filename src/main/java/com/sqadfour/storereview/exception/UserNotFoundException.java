package com.sqadfour.storereview.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(Integer userId) {
		super(String.format("User with Id %d not found", userId));
	}
}
