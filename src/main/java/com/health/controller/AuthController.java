/**
 * 
 */
package com.health.controller;

import java.util.List;

import com.health.dto.request.SocialLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.health.dto.MessageResponse;
import com.health.dto.ResetPasswordRequest;
import com.health.dto.SignInRequest;
import com.health.dto.TokenResponse;
import com.health.dto.request.LoginRequest;
import com.health.dto.request.UserRegistrationRequest;
import com.health.dto.request.UserSignUpRequest;
import com.health.dto.response.KycStepResponse;
import com.health.dto.response.UserRegisteredResponse;
import com.health.dto.response.UserResponse;
import com.health.enums.LoginType;
import com.health.models.ApiResponse;
import com.health.service.AuthenticationService;
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
	private AuthenticationService authenticationService;

	@Autowired
	private SignUpService signUpService;

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private KycStepService kycStepService;

	@GetMapping("/user/{providerLoginId}")
	public ResponseEntity<ApiResponse<UserRegisteredResponse>> isRegisteredUser(@PathVariable String providerLoginId) {
		return userRegistrationService.isRegister(providerLoginId);
	}

	@PostMapping("/password/reset")
	public ResponseEntity<ApiResponse<MessageResponse>> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
		return userRegistrationService.resetPassword(resetPasswordRequest);
	}

	@PostMapping("/user/signup")
	public ResponseEntity<ApiResponse<UserResponse>> userSignUp(@RequestBody UserSignUpRequest request) {
		UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest(request.getUserName(), null,
				request.getProviderLoginId(), null, null, LoginType.MANUAL, request.getPassword(), 4);
		return userRegistrationService.register(userRegistrationRequest);
	}

	@PostMapping("/user/login")
	public ResponseEntity<ApiResponse<UserResponse>> patientLogin(@RequestBody SignInRequest request) {
		LoginRequest loginRequest = new LoginRequest(request.getProviderLoginId(), request.getPassword(), 4L);
		return authenticationService.authenticate(loginRequest);
	}

	@PostMapping("/doctors/signup")
	public ResponseEntity<ApiResponse<UserResponse>> doctorSignUp(@RequestBody UserSignUpRequest request) {
		UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest(request.getUserName(), null,
				request.getProviderLoginId(), null, null, LoginType.MANUAL, request.getPassword(), 3);
		return userRegistrationService.register(userRegistrationRequest);
	}

	@PostMapping("/employees/login")
	public ResponseEntity<ApiResponse<UserResponse>> employeeLogin(@RequestBody SignInRequest request) {
		LoginRequest loginRequest = new LoginRequest(request.getProviderLoginId(), request.getPassword(), 2L);
		return authenticationService.authenticate(loginRequest);
	}

	@PostMapping("/user/otp/send")
	public ResponseEntity<ApiResponse<TokenResponse>> sendOtp(@RequestBody SignInRequest request) {
		return loginService.login(request);
	}

	@PostMapping("/user/otp/verify")
	public ResponseEntity<ApiResponse<TokenResponse>> verifyOtp(@RequestBody SignInRequest request) {
		return loginService.login(request);
	}

	@PostMapping("/login/google")
	public ApiResponse<UserResponse> googleLogin(@RequestBody SocialLoginRequest socialLoginRequest) {
		UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest(socialLoginRequest.getSocialId(),LoginType.GOOGLE,4,socialLoginRequest.getData());
		return authenticationService.googleAuthenticate(userRegistrationRequest);
	}
	
	@GetMapping("/user/steps/{userId}")
	public ResponseEntity<ApiResponse<List<KycStepResponse>>> allSteps(@PathVariable(name = "userId") Long userId) {
		return kycStepService.allSteps(userId);
	}
}