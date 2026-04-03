package com.health.service;

import com.health.dto.request.AuthRequest;
import com.health.dto.response.AuthResponse;
import com.health.models.ApiResponse;

public interface AuthStrategy {
    ApiResponse<AuthResponse> authenticate(AuthRequest request);
}

