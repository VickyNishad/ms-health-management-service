/**
 * 
 */
package com.health.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.health.dto.MessageResponse;
import com.health.models.ApiResponse;
import com.health.service.OTPService;

/**
 * 
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/otp")
public class OTPController {

	@Autowired
	private OTPService otpService;
	
	@PostMapping("/sms/send")
	public ResponseEntity<ApiResponse<MessageResponse>> sendOTP(@RequestParam String mobileNumber) {
		return otpService.sendOTP(mobileNumber);
	}
	
	@PostMapping("/sms/verify")
	public ResponseEntity<ApiResponse<MessageResponse>> verifyOTP(@RequestParam String mobileNumber,@RequestParam String otp) {
		return otpService.verifyOTP(mobileNumber,otp);
	}

}
