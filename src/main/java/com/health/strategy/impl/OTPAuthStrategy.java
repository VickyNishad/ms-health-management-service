package com.health.strategy.impl;

import com.health.dto.response.MessageResponse;
import com.health.dto.request.AuthRequest;
import com.health.dto.request.CreateUserRequest;
import com.health.dto.response.AuthResponse;
import com.health.dto.response.UserResponseDTO;
import com.health.entity.User;
import com.health.mappers.AuthMapper;
import com.health.mappers.UserMapper;
import com.health.models.ApiResponse;
import com.health.strategy.AuthStrategy;
import com.health.service.OTPService;
import com.health.service.RoleMasterService;
import com.health.service.UserService;
import com.health.utility.ApiExecutionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("otp")
public class OTPAuthStrategy implements AuthStrategy {

    @Autowired
    private OTPService otpService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleMasterService roleMasterService;

    private final UserMapper userMapper;
    private final AuthMapper authMapper;

    public OTPAuthStrategy(UserMapper userMapper, AuthMapper authMapper) {
        this.userMapper = userMapper;
        this.authMapper = authMapper;
    }

    @Override
    public ApiResponse<AuthResponse> authenticate(AuthRequest request) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null, req -> {
            if (request == null) {
                throw new IllegalArgumentException("request is null");
            }
            if (request.getLoginType() == null) {
                throw new IllegalArgumentException("loginType is null");
            }
            if (request.getMobileNumber() == null) {
                throw new IllegalArgumentException("mobileNumber is null");
            }
            if(request.getOtpCode() == null){
                throw new IllegalArgumentException("OTP code is required");
            }
        }, () -> {
            User user;
            // Verify otp
            ApiResponse<MessageResponse> apiResponse = otpService.verifyOTP(request.getMobileNumber(), request.getOtpCode());
            // false
            if (!apiResponse.isSuccess()) {
                throw new RuntimeException(apiResponse.getMessage());
            }
            // true
            // check register user or not
            ApiResponse<User> apiUserResponse = userService.findByMobileNumber(request.getMobileNumber());
            if (!apiUserResponse.isSuccess()) {
                // Get RoleId from Role Master
                // Register
                CreateUserRequest createUserRequest = new CreateUserRequest();
                createUserRequest.setSocialId(request.getSocialId());
                createUserRequest.setPassword(request.getPassword());
                createUserRequest.setUserName(request.getFirstName());
                createUserRequest.setLoginType(request.getLoginType());
                ApiResponse<UserResponseDTO> userApiResponse = userService.createUser(createUserRequest);
                if (!userApiResponse.isSuccess()) {
                    throw new RuntimeException(userApiResponse.getMessage());
                }

                user = userMapper.toEntity(createUserRequest);
                user.setId(userApiResponse.getData().getId());

            } else {
                user = apiUserResponse.getData();
            }
            return authMapper.toAuthResponse(user);
        }, ApiResponse::success);
    }

}
