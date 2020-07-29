package com.sqad4.StoreReview.utility;


import com.sqad4.StoreReview.constant.AppConstant;
import com.sqad4.StoreReview.dto.ResponseDto;

/**
 * This class is used for common application coding.
 * 
 * @author vijay
 * @since 24-07-2020
 */
public class UtilityService {
	/**
	 * This method is used to retuen the proper response to user.
	 * @param message
	 * @return
	 */
	public static ResponseDto responseDto(String message) {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setResponseMessage(message);
		responseDto.setStatusMessage(AppConstant.SUCCESS_STATUS_MESSAGE);
		responseDto.setStatusCode(200);
		return responseDto;
	}

}
