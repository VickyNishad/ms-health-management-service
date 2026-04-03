/**
 * 
 */
package com.health.controller;

import com.health.dto.request.*;
import com.health.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.health.dto.SignInRequest;
import com.health.dto.TokenResponse;
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
	public ResponseEntity<ApiResponse<UserRegisteredResponse>> isUserRegistered(@PathVariable String providerLoginId) {
		return userService.isUserRegistered(providerLoginId);
	}

//	@PostMapping("/user/password/reset")
//	public ResponseEntity<ApiResponse<MessageResponse>> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
//		UserAuthRequest userAuthRequest = new UserAuthRequest();
//		userAuthRequest.setProviderLoginId(resetPasswordRequest.getProviderLoginId());
//		passwordAuthService.authenticate(userAuthRequest);
//		return userService.resetPassword(resetPasswordRequest);
//	}
//
//	@PostMapping("/user/signup")
//	public ResponseEntity<ApiResponse<UserResponseDTO>> userSignUp(@RequestBody UserSignUpRequest request) {
//		CreateUserRequestDTO createUserRequestDTO = new CreateUserRequestDTO(request.getUserName(),
//				null,
//				request.getProviderLoginId(),
//				null,
//				null,
//				LoginType.MANUAL,
//				request.getPassword(),
//				4);
//		return userService.register(createUserRequestDTO);
//	}

//	@PostMapping("/user/login")
//	public ResponseEntity<ApiResponse<UserResponseDTO>> patientLogin(@RequestBody SignInRequest request) {
//		LoginRequest loginRequest = new LoginRequest(request.getProviderLoginId(), request.getPassword(), 4L);
//		return authService.authenticate(loginRequest);
//	}

//	@PostMapping("/doctors/signup")
//	public ResponseEntity<ApiResponse<UserResponseDTO>> doctorSignUp(@RequestBody UserSignUpRequest request) {
//		CreateUserRequestDTO createUserRequestDTO = new CreateUserRequestDTO(request.getUserName(), null,
//				request.getProviderLoginId(), null, null, LoginType.MANUAL, request.getPassword(), 3);
//		return userService.register(createUserRequestDTO);
//	}


//	@PostMapping("/user/otp/send")
//	public ResponseEntity<ApiResponse<TokenResponse>> sendOtp(@RequestBody SignInRequest request) {
//		return loginService.login(request);
//	}
//
//	@PostMapping("/user/otp/verify")
//	public ResponseEntity<ApiResponse<TokenResponse>> verifyOtp(@RequestBody SignInRequest request) {
//		return loginService.login(request);
//	}

//	@PostMapping("/login/social")
//	public ApiResponse<UserResponseDTO> social(@RequestBody SocialAuthRequest socialAuthRequest) {
//		CreateUserRequestDTO createUserRequestDTO = new CreateUserRequestDTO(socialAuthRequest.getSocialId(),LoginType.GOOGLE,4, socialAuthRequest.getData());
//		return authService.googleAuthenticate(createUserRequestDTO);
//	}
}