package com.health.strategy.impl;

import com.health.dto.request.AuthRequest;
import com.health.dto.request.CreateUserRequest;
import com.health.dto.response.AuthResponse;
import com.health.dto.response.UserResponseDTO;
import com.health.entity.User;
import com.health.mappers.AuthMapper;
import com.health.mappers.UserMapper;
import com.health.dto.response.ApiResponse;
import com.health.strategy.AuthStrategy;
import com.health.service.RoleMasterService;
import com.health.service.UserService;
import com.health.utility.ApiExecutionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("google")
public class GoogleAuthStrategy implements AuthStrategy {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMasterService roleMasterService;

    private final UserMapper userMapper;
    private final AuthMapper authMapper;

    public GoogleAuthStrategy(UserMapper userMapper, AuthMapper authMapper) {
        this.userMapper = userMapper;
        this.authMapper = authMapper;
    }

    @Override
    public ApiResponse<AuthResponse> authenticate(AuthRequest request) {

        return ApiExecutionUtils.ApiExecutor.processRequest(null,
                req -> {
                },
                () -> {
                    User user;
                    ApiResponse<User> apiResponse = userService.findBySocialId(request.getSocialId());
                    if (!apiResponse.isSuccess()) {
                        // Get RoleId from Role Master

                        // Register
                        CreateUserRequest createUserRequest = new CreateUserRequest();
                        createUserRequest.setSocialId(request.getSocialId());
                        createUserRequest.setPassword(request.getPassword());
                        createUserRequest.setUserName(request.getFirstName());
                        createUserRequest.setLoginType(request.getLoginType());
                        createUserRequest.setRole(request.getRole());

                        ApiResponse<UserResponseDTO> userApiResponse = userService.createUser(createUserRequest);
                        if (!userApiResponse.isSuccess()) {
                            throw new RuntimeException(userApiResponse.getMessage());
                        }

                        user = userMapper.toEntity(createUserRequest);
                        user.setId(userApiResponse.getData().getId());

                    } else {
                        user = apiResponse.getData();
                    }
                    return authMapper.toAuthResponse(user);
                },
                ApiResponse::success
        );
    }
}
