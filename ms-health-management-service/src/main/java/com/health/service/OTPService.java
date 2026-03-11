/**
 * 
 */
package com.health.service;

/**
 * 
 */
public interface OTPService {

	public String sendOTP(String mobileNumber);
	public boolean verifyOTP(String mobileNumber,String otp);
	
	boolean existsByMobileNumber(String mobileNumber);

	boolean existsByEmailId(String emailId);
}
