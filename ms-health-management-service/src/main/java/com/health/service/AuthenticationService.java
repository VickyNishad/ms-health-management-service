/**
 * 
 */
package com.health.service;

import org.springframework.http.ResponseEntity;

import com.health.dto.request.LoginRequest;
import com.health.dto.response.UserResponse;
import com.health.models.ApiResponse;

/**
 * 
 */
public interface AuthenticationService {

	public ResponseEntity<ApiResponse<UserResponse>> autenticate(LoginRequest loginRequest);

}
