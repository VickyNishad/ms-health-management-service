package com.health.service;

import com.health.dto.response.MessageResponse;
import com.health.dto.request.ResetPasswordRequest;
import com.health.models.ApiResponse;

public interface ResetPasswordService {
    ApiResponse<MessageResponse> resetPassword(ResetPasswordRequest restPasswordRequest);
}
