/**
 * 
 */
package com.health.service;

import com.health.dto.request.CreateUserRequestDTO;
import com.health.dto.request.UpdateUserRequest;
import com.health.dto.response.UserResponseDTO;
import com.health.entity.User;
import org.springframework.http.ResponseEntity;

import com.health.models.ApiResponse;

import com.health.dto.response.UserRegisteredResponse;

/**
 * 
 */
public interface UserService {

	ApiResponse<UserRegisteredResponse> isUserRegistered(String providerLoginId);
	ApiResponse<User> findByMobileNumber(String mobileNumber);
	ApiResponse<User> findByEmailId(String emailId);
	ApiResponse<User> findBySocialId(String socialId);
	ApiResponse<UserResponseDTO> createUser(CreateUserRequestDTO createUserRequestDTO);
	ApiResponse<UserResponseDTO> updateUser(Long id, UpdateUserRequest updateUserRequest);
	ApiResponse<UserResponseDTO> findUserById(Long id);

}
