/**
 *
 */
package com.health.service.impl;

import com.health.dto.request.AuthRequest;
import com.health.dto.response.AuthResponse;
import com.health.service.AuthStrategy;
import com.health.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.models.ApiResponse;
import com.health.service.AuthService;

/**
 *
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtService jwtService;

    private final AuthStrategyFactory factory;

    public AuthServiceImpl(AuthStrategyFactory factory) {
        this.factory = factory;
    }

    @Override
    public ApiResponse<AuthResponse> authenticate(AuthRequest authRequest) {
        AuthStrategy strategy = factory.getAuthStrategy(authRequest.getLoginType().toValue());
        ApiResponse<AuthResponse> apiResponse = strategy.authenticate(authRequest);
        AuthResponse authResponse = apiResponse.getData();
        authResponse.setActive(true);
        apiResponse.setData(authResponse);
        return apiResponse;
    }
}
