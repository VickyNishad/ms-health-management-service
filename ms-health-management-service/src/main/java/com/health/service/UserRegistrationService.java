/**
 * 
 */
package com.health.service;

import org.springframework.http.ResponseEntity;

import com.health.models.ApiResponse;


import com.health.dto.MessageResponse;
import com.health.dto.ResetPasswordRequest;
import com.health.dto.request.UserRegistrationRequest;
import com.health.dto.response.RegisteredUserResponse;

/**
 * 
 */
public interface UserRegistrationService {

	public ResponseEntity<ApiResponse<RegisteredUserResponse>> isRegister(String providerLofinId);
	public ResponseEntity<ApiResponse<MessageResponse>> resetPassword(ResetPasswordRequest restPasswordRequest);
	public ResponseEntity<ApiResponse<MessageResponse>> register(UserRegistrationRequest userRegistrationRequest);

}
