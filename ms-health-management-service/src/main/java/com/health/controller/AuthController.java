/**
 * 
 */
package com.health.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.health.dto.DoctorSignUpRequest;
import com.health.dto.MessageResponse;
import com.health.dto.PatientSignUpRequest;
import com.health.dto.ResetPasswordRequest;
import com.health.dto.SignInRequest;
import com.health.dto.TokenResponse;
import com.health.dto.response.KycStepResponse;
import com.health.dto.response.RegisteredUserResponse;
import com.health.enums.LoginType;
import com.health.enums.Role;
import com.health.models.ApiResponse;
import com.health.service.KycStepService;
import com.health.service.LoginService;
import com.health.service.SignUpService;
import com.health.service.UserRegistrationService;

/**
 * 
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private UserRegistrationService userRegistrationService;

	@Autowired
	private SignUpService signUpService;

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private KycStepService kycStepService;


	@GetMapping("/user/{providerLoginId}")
	public ResponseEntity<ApiResponse<RegisteredUserResponse>> isRegisteredUser(@PathVariable String providerLoginId) {
		return userRegistrationService.isRegister(providerLoginId);
	}


	@PostMapping("/password/reset")
	public ResponseEntity<ApiResponse<MessageResponse>> resetPassword(
			@RequestBody ResetPasswordRequest resetPasswordRequest) {
		return userRegistrationService.resetPassword(resetPasswordRequest);
	}

	@PostMapping("/user/signup")
	public ResponseEntity<ApiResponse<TokenResponse>> patientSignUp(@RequestBody PatientSignUpRequest request) {

		request.setRole(Role.PATIENT);
		request.setLoginType(LoginType.MANUAL);

		return signUpService.patientSignUp(request);
	}


	@PostMapping("/user/login")
	public ResponseEntity<ApiResponse<TokenResponse>> patientLogin(@RequestBody SignInRequest request) {

		request.setRole(Role.PATIENT);

		return loginService.login(request);
	}


	@PostMapping("/doctors/signup")
	public ResponseEntity<ApiResponse<TokenResponse>> doctorSignUp(@RequestBody DoctorSignUpRequest request) {

		request.setRole(Role.DOCTOR);
		request.setLoginType(LoginType.MANUAL);

		return signUpService.doctorSignUp(request);
	}

	@PostMapping("/employees/login")
	public ResponseEntity<ApiResponse<TokenResponse>> employeeLogin(@RequestBody SignInRequest request) {

		request.setRole(Role.DOCTOR);

		return loginService.login(request);
	}


	@PostMapping("/user/otp/send")
	public ResponseEntity<ApiResponse<TokenResponse>> sendOtp(@RequestBody SignInRequest request) {

		return loginService.login(request);
	}


	@PostMapping("/user/otp/verify")
	public ResponseEntity<ApiResponse<TokenResponse>> verifyOtp(@RequestBody SignInRequest request) {

		return loginService.login(request);
	}


	@PostMapping("/login/social/{providerLoginId}")
	public ResponseEntity<RegisteredUserResponse> socialLogin(@PathVariable String providerLoginId) {

		return null;
	}
	
	@GetMapping("/user/steps/{userId}")
	public ResponseEntity<ApiResponse<List<KycStepResponse>>> allSteps(@PathVariable(name = "userId") Long userId) {
		return kycStepService.allSteps(userId);
	}
}