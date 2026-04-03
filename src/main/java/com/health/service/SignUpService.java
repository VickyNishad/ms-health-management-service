/**
 * 
 */
package com.health.service;

import com.health.dto.request.SignUpRequest;
import com.health.dto.response.AuthResponse;

import com.health.models.ApiResponse;


/**
 * 
 */
public interface SignUpService {
	
	public ApiResponse<AuthResponse> signUp(SignUpRequest signUpRequest);
}
