package com.health.service;

import com.health.dto.MessageResponse;
import com.health.dto.ResetPasswordRequest;
import com.health.models.ApiResponse;

public interface ResetPasswordService {
    ApiResponse<MessageResponse> resetPassword(ResetPasswordRequest restPasswordRequest);
}
