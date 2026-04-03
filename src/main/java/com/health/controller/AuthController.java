/**
 * 
 */
package com.health.controller;

import com.health.dto.request.*;
import com.health.dto.response.AuthResponse;
import com.health.enums.LoginType;
import com.health.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.health.dto.response.UserRegisteredResponse;
import com.health.models.ApiResponse;

/**
 * 
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;

	@Autowired
	private SignUpService signUpService;


	@GetMapping("/user/exist/{providerLoginId}")
	public ApiResponse<UserRegisteredResponse> isUserRegistered(@PathVariable String providerLoginId) {
		return userService.isUserRegistered(providerLoginId);
	}

	@PostMapping("/user/otp/login")
	public ApiResponse<AuthResponse> loginWithOtpCode(@RequestBody UpdateOtpRequest updateOtpRequest) {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setMobileNumber(updateOtpRequest.getMobileNumber());
		authRequest.setOtpCode(updateOtpRequest.getOtp());
		authRequest.setLoginType(LoginType.OTP);
		return authService.authenticate(authRequest);
	}

	@PostMapping("/user/login")
	public ApiResponse<AuthResponse> passwordLogin(@RequestBody LoginRequest loginRequest) {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setMobileNumber(loginRequest.getProviderLoginId());
		authRequest.setPassword(loginRequest.getPassword());
		authRequest.setLoginType(LoginType.PASSWORD);
		return authService.authenticate(authRequest);
	}

}