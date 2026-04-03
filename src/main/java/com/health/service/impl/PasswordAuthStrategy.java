package com.health.service.impl;

import com.health.dto.request.AuthRequest;
import com.health.dto.response.AuthResponse;
import com.health.entity.User;
import com.health.mappers.AuthMapper;
import com.health.models.ApiResponse;
import com.health.service.AuthStrategy;
import com.health.service.UserService;
import com.health.utility.ApiExecutionUtils;
import com.health.utility.HealthUtils;
import org.springframework.stereotype.Component;

@Component("password")
public class PasswordAuthStrategy implements AuthStrategy {

    private final UserService userService;
    private final AuthMapper authMapper;

    public PasswordAuthStrategy(UserService userService, AuthMapper authMapper) {
        this.userService = userService;
        this.authMapper = authMapper;
    }

    @Override
    public ApiResponse<AuthResponse> authenticate(AuthRequest request) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null, req -> {
                },
                () -> {
                    ApiResponse<User> apiResponse = userService.findByMobileNumber(request.getMobileNumber());
                    if (!apiResponse.isSuccess()) {
                        throw new RuntimeException(apiResponse.getMessage());
                    }
                    User user = apiResponse.getData();

                    String password = request.getPassword();
                    String hashPassword = user.getPassword();

                    if (!HealthUtils.matchPassword(password, hashPassword)) {
                        throw new RuntimeException("Invalid password. Please try again.");
                    }

                    return authMapper.toAuthResponse(user);
                }, ApiResponse::success);
    }

}