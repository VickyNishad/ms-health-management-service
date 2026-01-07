/**
 * 
 */
package com.health.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.common.models.ApiResponse;
import com.health.dto.DoctorSignUpRequest;
import com.health.dto.PatientSignUpRequest;
import com.health.dto.TokenResponse;


/**
 * 
 */
public interface SignUpService {
	
	public ResponseEntity<ApiResponse<TokenResponse>> patientSignUp(@RequestBody PatientSignUpRequest signUpRequest);
	public ResponseEntity<ApiResponse<TokenResponse>> doctorSignUp(@RequestBody DoctorSignUpRequest signUpRequest);
}
