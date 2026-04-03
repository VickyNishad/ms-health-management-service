package com.health.service;

import com.health.dto.ResetPasswordRequest;
import com.health.dto.response.AuthResponse;
import com.health.models.ApiResponse;

public interface PasswordAuthService extends AuthStrategy {

    ApiResponse<AuthResponse> resetPassword(ResetPasswordRequest request);
}
