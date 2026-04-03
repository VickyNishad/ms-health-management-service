/**
 * 
 */
package com.health.service;

import com.health.dto.request.AuthRequest;
import com.health.dto.response.AuthResponse;

import com.health.dto.response.ApiResponse;

/**
 * 
 */
public interface AuthService {
	ApiResponse<AuthResponse> authenticate(AuthRequest authRequest);
}
