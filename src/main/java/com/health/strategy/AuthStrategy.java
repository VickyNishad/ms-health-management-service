package com.health.strategy;

import com.health.dto.request.AuthRequest;
import com.health.dto.response.AuthResponse;
import com.health.dto.response.ApiResponse;

public interface AuthStrategy {
    ApiResponse<AuthResponse> authenticate(AuthRequest request);
}

