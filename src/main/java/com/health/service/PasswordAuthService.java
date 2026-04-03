package com.health.service;

import com.health.dto.request.ResetPasswordRequest;
import com.health.dto.response.AuthResponse;
import com.health.models.ApiResponse;
import com.health.strategy.AuthStrategy;

public interface PasswordAuthService extends AuthStrategy {

    ApiResponse<AuthResponse> resetPassword(ResetPasswordRequest request);
}
