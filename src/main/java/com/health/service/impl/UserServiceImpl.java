/**
 *
 */
package com.health.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import com.health.dto.request.CreateUserRequest;
import com.health.dto.request.UpdateUserRequest;
import com.health.dto.response.UserResponseDTO;
import com.health.entity.User;
import com.health.mappers.UserMapper;
import com.health.repository.UserRepository;
import com.health.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.dto.response.UserRegisteredResponse;
import com.health.entity.RoleMaster;
import com.health.dto.response.ApiResponse;
import com.health.utility.ApiExecutionUtils;

/**
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleMasterService roleMasterService;

    @Autowired
    private KycStepService kycStepService;

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public ApiResponse<UserRegisteredResponse> isUserRegistered(String providerLoginId) {
        // TODO Auto-generated method stub
        UserRegisteredResponse userRegisteredResponse = new UserRegisteredResponse();
        return ApiExecutionUtils.ApiExecutor.processRequest(providerLoginId, req -> {
        }, () -> {
            Optional<User> user = userRepository.findByMobileNumber(providerLoginId);
            if (user.isPresent()) {
                User userData = user.get();

                userRegisteredResponse.setUserName(userData.getUserName());
                userRegisteredResponse.setIsActive(userData.getIsActive());
                userRegisteredResponse.setIsRegistered(userData.getIsActive());
                userRegisteredResponse.setLoginType(userData.getLoginType());

                RoleMaster roleMaster = userData.getRole();

                userRegisteredResponse.setRole(roleMaster.getRoleName());
                userRegisteredResponse.setRoleId(roleMaster.getId());

            } else {
                throw new RuntimeException("User not found. Please create an account to proceed.");
            }
            return userRegisteredResponse;
        }, ApiResponse::success);
    }


    @Override
    public ApiResponse<User> findByMobileNumber(String mobileNumber) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,
                req -> {
                },
                () -> {
                    Optional<User> user = userRepository.findByMobileNumber(mobileNumber);
                    return user.orElseThrow(() -> new RuntimeException("User not found"));
                }, ApiResponse::success);
    }

    @Override
    public ApiResponse<User> findByEmailId(String emailId) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,
                req -> {
                },
                () -> {
                    Optional<User> user = userRepository.findByEmailId(emailId);
                    return user.orElseThrow(() -> new RuntimeException("User not found"));
                }, ApiResponse::success);
    }

    @Override
    public ApiResponse<User> findBySocialId(String socialId) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,
                req -> {
                },
                () -> {
                    Optional<User> user = userRepository.findBySocialId(socialId);
                    return user.orElseThrow(() -> new RuntimeException("User not found"));
                }, ApiResponse::success);
    }

    @Override
    public ApiResponse<UserResponseDTO> createUser(CreateUserRequest createUserRequest) {
        return ApiExecutionUtils.ApiExecutor.processRequest(createUserRequest, req -> {
            // Validate all request parameters
                },
                () -> {
                    ApiResponse<RoleMaster> apiResponse = roleMasterService.findByRole(createUserRequest.getRole().toValue());
                    if (!apiResponse.isSuccess()) {
                        throw new RuntimeException(apiResponse.getMessage());
                    }
                    User user = userMapper.toEntity(createUserRequest);
                    user.setRole(apiResponse.getData());
                    ApiResponse<User> apiUserRes = createUser(user);

                    if (!apiUserRes.isSuccess()) {
                        throw new RuntimeException(apiUserRes.getMessage());
                    }
                    return userMapper.toDTO(user);
                },
                ApiResponse::success);
    }

    @Override
    public ApiResponse<User> createUser(User user) {
        return ApiExecutionUtils.ApiExecutor.processRequest(user, req -> {
            // Validate all request parameters
                },
                () -> {
                    return userRepository.save(user);
                },
                ApiResponse::success);
    }

    @Override
    public ApiResponse<UserResponseDTO> updateUser(Long id, UpdateUserRequest updateUserRequest) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null, req -> {
                },
                () -> {
                    Optional<User> optionalUser = userRepository.findById(id);
                    if (optionalUser.isEmpty()) {
                        throw new RuntimeException("User not found");
                    }
                    User user = optionalUser.get();
                    user.setUserName(updateUserRequest.getUserName());
                    user.setEmailId(updateUserRequest.getEmailId());
                    user.setPassword(updateUserRequest.getPassword());
                    user.setUpdatedAt(LocalDateTime.now());
                    user.setUpdatedBy(id.toString());
                    user = userRepository.save(user);

                    return userMapper.toDTO(user);
                },
                ApiResponse::success);
    }

    @Override
    public ApiResponse<UserResponseDTO> findUserById(Long id) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null, req -> {
                },
                () -> {
                    Optional<User> user = userRepository.findById(id);
                    if (user.isEmpty()) {
                        throw new RuntimeException("User not found");
                    }
                    return userMapper.toDTO(user.get());
                }, ApiResponse::success);
    }
}
