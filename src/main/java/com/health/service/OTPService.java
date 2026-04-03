/**
 * 
 */
package com.health.service;

import com.health.dto.response.MessageResponse;
import com.health.models.ApiResponse;

/**
 * 
 */
public interface OTPService {

	ApiResponse<MessageResponse> sendOTP(String mobileNumber);
	ApiResponse<MessageResponse> verifyOTP(String mobileNumber,String otp);
	boolean existsByMobileNumber(String mobileNumber);
	boolean existsByEmailId(String emailId);
}
