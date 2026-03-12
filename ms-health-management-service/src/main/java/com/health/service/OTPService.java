/**
 * 
 */
package com.health.service;

import org.springframework.http.ResponseEntity;

import com.health.dto.MessageResponse;
import com.health.models.ApiResponse;

/**
 * 
 */
public interface OTPService {

	public ResponseEntity<ApiResponse<MessageResponse>> sendOTP(String mobileNumber);
	public ResponseEntity<ApiResponse<MessageResponse>> verifyOTP(String mobileNumber,String otp);
	
	boolean existsByMobileNumber(String mobileNumber);

	boolean existsByEmailId(String emailId);
}
