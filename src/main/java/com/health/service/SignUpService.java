/**
 * 
 */
package com.health.service;

import com.health.dto.UserRegistrationRequest;
import com.health.dto.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.health.models.ApiResponse;

import com.health.dto.DoctorSignUpRequest;
import com.health.dto.PatientSignUpRequest;
import com.health.dto.TokenResponse;


/**
 * 
 */
public interface SignUpService {
	
	public ApiResponse<AuthResponse> signUp(UserRegistrationRequest signUpRequest);
}
