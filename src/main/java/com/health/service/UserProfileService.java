/**
 * 
 */
package com.health.service;

import com.health.dto.request.UserProfileRequest;

import com.health.dto.response.ProfileDetailsResponse;
import com.health.entity.User;
import com.health.dto.response.ApiResponse;

/**
 * 
 */
public interface UserProfileService {
	
	public ApiResponse<ProfileDetailsResponse> createNewProfile(Long userId, UserProfileRequest userProfileRequest);
	public ApiResponse<ProfileDetailsResponse> findUserProfileById(Long userId);
	public ApiResponse<ProfileDetailsResponse> updateProfile(Long userId, UserProfileRequest userProfileRequest);

}
