/**
 *
 */
package com.health.controller;

import com.health.dto.response.MessageResponse;
import com.health.dto.request.ResetPasswordRequest;
import com.health.dto.request.*;
import com.health.dto.response.AuthResponse;
import com.health.enums.LoginType;
import com.health.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.health.dto.response.UserRegisteredResponse;
import com.health.dto.response.ApiResponse;

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

    @Autowired
    private ResetPasswordService resetPasswordService;


    @GetMapping("/user/exist/{providerLoginId}")
    public ApiResponse<UserRegisteredResponse> isUserRegistered(@PathVariable String providerLoginId) {
        return userService.isUserRegistered(providerLoginId);
    }

    @PostMapping("/user/signup")
    public ApiResponse<AuthResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        return signUpService.signUp(signUpRequest);
    }

    @PostMapping("/user/login")
    public ApiResponse<AuthResponse> passwordLogin(@RequestBody LoginRequest loginRequest) {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setMobileNumber(loginRequest.getProviderLoginId());
        authRequest.setPassword(loginRequest.getPassword());
        authRequest.setLoginType(LoginType.PASSWORD);
        return authService.authenticate(authRequest);
    }

    @PostMapping("/user/otp/login")
    public ApiResponse<AuthResponse> loginWithOtpCode(@RequestBody UpdateOtpRequest updateOtpRequest) {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setMobileNumber(updateOtpRequest.getMobileNumber());
        authRequest.setOtpCode(updateOtpRequest.getOtp());
        authRequest.setLoginType(LoginType.OTP);
        return authService.authenticate(authRequest);
    }


    @PostMapping("/user/social/login")
    public ApiResponse<AuthResponse> socialLogin(@RequestBody SocialAuthRequest socialAuthRequest) {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setSocialId(socialAuthRequest.getSocialId());
        authRequest.setLoginType(socialAuthRequest.getLoginType());
        return authService.authenticate(authRequest);
    }


    @PostMapping("/user/reset/password")
    public ApiResponse<MessageResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return resetPasswordService.resetPassword(resetPasswordRequest);
    }

}