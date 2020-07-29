package com.sqad4.StoreReview.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(Integer userId) {
		super(String.format("User with Id %d not found", userId));
	}
}
