package com.sqadfour.storereview.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sqadfour.storereview.constant.AppConstant;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	/**
	 * to handle null pointer exception
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Object> nullPointerException(NullPointerException ex, WebRequest wr) {
		Map<String, Object> responseMap = new LinkedHashMap<>();
		responseMap.put(AppConstant.STATUS_CODE, AppConstant.STATUS_CODE_VALUE);
		responseMap.put(AppConstant.STATUS_MESSAGE, AppConstant.EMPLTY_DATA_MESSAGE);
		return new ResponseEntity<>(responseMap, HttpStatus.NOT_FOUND);
	}

	/**
	 * to handle null general exception
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> generalException(Exception ex, WebRequest wr) {
		Map<String, Object> responseMap = new LinkedHashMap<>();
		responseMap.put(AppConstant.STATUS_CODE, AppConstant.STATUS_CODE_VALUE);
		responseMap.put(AppConstant.STATUS_MESSAGE, AppConstant.SOMETHINGWRONG_MESSAGE);
		return new ResponseEntity<>(responseMap, HttpStatus.NOT_FOUND);
	}

	/**
	 * to handle user Not found exception
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> userNotFoundException(UserNotFoundException ex, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put(AppConstant.STATUS_CODE, AppConstant.STATUS_CODE_VALUE);
		body.put(AppConstant.STATUS_MESSAGE, AppConstant.USER_NOT_FOUND_MESSAGE);
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	/**
	 * to handle product Not found exception
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<Object> productNotFoundException(ProductNotFoundException ex, WebRequest request) {
		
		Map<String, Object> body = new LinkedHashMap<>();
		body.put(AppConstant.STATUS_CODE, AppConstant.STATUS_CODE_VALUE);
		body.put(AppConstant.STATUS_MESSAGE, AppConstant.PRODUCT_NOT_FOUND_MESSAGE);
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
}
