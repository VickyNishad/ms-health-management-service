/**
 *
 */
package com.health.service;

import com.health.dto.request.CreateUserRequest;
import com.health.dto.request.UpdateUserRequest;
import com.health.dto.response.UserResponseDTO;
import com.health.entity.User;

import com.health.dto.response.ApiResponse;

import com.health.dto.response.UserRegisteredResponse;

/**
 *
 */
public interface UserService {

    ApiResponse<UserRegisteredResponse> isUserRegistered(String providerLoginId);

    ApiResponse<User> findByMobileNumber(String mobileNumber);

    ApiResponse<User> findByEmailId(String emailId);

    ApiResponse<User> findBySocialId(String socialId);

    ApiResponse<UserResponseDTO> createUser(CreateUserRequest createUserRequest);

    ApiResponse<User> createUser(User user);

    ApiResponse<UserResponseDTO> updateUser(Long id, UpdateUserRequest updateUserRequest);

    ApiResponse<UserResponseDTO> findUserById(Long id);

}
