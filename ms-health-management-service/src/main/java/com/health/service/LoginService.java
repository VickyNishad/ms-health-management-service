/**
 * 
 */
package com.health.service;

import org.springframework.http.ResponseEntity;

import com.health.models.ApiResponse;

import com.health.dto.SignInRequest;
import com.health.dto.TokenResponse;


/**
 * 
 */
public interface LoginService {
	

	public ResponseEntity<ApiResponse<TokenResponse>> handleSocialSign(SignInRequest signInRequest);
	
	public ResponseEntity<ApiResponse<TokenResponse>> login(SignInRequest signInRequest);

}
