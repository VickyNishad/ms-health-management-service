/**
 *
 */
package com.health.service.impl;

import com.health.dto.request.SignUpRequest;
import com.health.dto.request.CreateUserRequest;
import com.health.dto.response.AuthResponse;
import com.health.dto.response.UserResponseDTO;
import com.health.entity.RoleMaster;
import com.health.entity.User;
import com.health.mappers.AuthMapper;
import com.health.models.TokenModel;
import com.health.service.RoleMasterService;
import com.health.service.UserService;
import com.health.utility.ApiExecutionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.health.dto.TokenResponse;
import com.health.models.ApiResponse;
import com.health.service.JwtService;
import com.health.service.SignUpService;

/**
 *
 */
@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMasterService roleMasterService;

    private final AuthMapper authMapper;

    public SignUpServiceImpl(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    @Override
    public ApiResponse<AuthResponse> signUp(SignUpRequest signUpRequest) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null, req -> {
        }, () -> {

            ApiResponse<User> apiResponse = userService.findByMobileNumber(signUpRequest.getProviderLoginId());
            if (apiResponse.isSuccess()) {
                throw new RuntimeException("You already have an account. Please log in to continue.");
            }

            ApiResponse<RoleMaster> roleMasterApiResponse = roleMasterService.findByRole(signUpRequest.getRole().toValue());
            if (!roleMasterApiResponse.isSuccess()) {
                throw new RuntimeException(roleMasterApiResponse.getMessage());
            }
            RoleMaster roleMaster = roleMasterApiResponse.getData();

            CreateUserRequest createUserRequest = new CreateUserRequest();
            createUserRequest.setUserName(signUpRequest.getUserName());
            createUserRequest.setPassword(signUpRequest.getPassword());
            createUserRequest.setMobileNumber(apiResponse.getData().getMobileNumber());
            createUserRequest.setLoginType(signUpRequest.getLoginType());
            createUserRequest.setRoleId(roleMaster.getId());

            ApiResponse<UserResponseDTO> responseDTOApiResponse = userService.createUser(createUserRequest);
            if (!responseDTOApiResponse.isSuccess()) {
                throw new RuntimeException("Failed to sign up with provider " + signUpRequest.getProviderLoginId());
            }
            ApiResponse<User> apiUserResponse = userService.findByMobileNumber(signUpRequest.getProviderLoginId());
            if (!apiUserResponse.isSuccess()) {
                throw new RuntimeException("Failed to sign up with provider " + signUpRequest.getProviderLoginId());
            }

            TokenModel tokenModel = new TokenModel();
            tokenModel.setUserId(apiUserResponse.getData().getId());
            tokenModel.setLoginType(apiUserResponse.getData().getLoginType());
            tokenModel.setRole(apiUserResponse.getData().getRole().getRoleName());
            tokenModel.setUserName(apiUserResponse.getData().getUserName());

            TokenResponse tokenResponse = jwtService.generateToken(tokenModel);
            AuthResponse authResponse = authMapper.toAuthResponse(apiUserResponse.getData());
            authResponse.setToken(tokenResponse);
            return authResponse;
        }, ApiResponse::success);
    }

}
