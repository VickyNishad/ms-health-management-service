/**
 * 
 */
package com.health.service;

import com.health.dto.request.AuthRequest;
import com.health.dto.request.CreateUserRequestDTO;
import com.health.dto.request.UserAuthRequest;
import com.health.dto.response.AuthResponse;
import com.health.dto.response.UserResponseDTO;
import org.springframework.http.ResponseEntity;

import com.health.dto.request.LoginRequest;
import com.health.models.ApiResponse;

/**
 * 
 */
public interface AuthService {
	ApiResponse<AuthResponse> authenticate(AuthRequest authRequest);
}
