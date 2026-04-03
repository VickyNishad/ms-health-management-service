/**
 * 
 */
package com.health.service;

import com.health.dto.MessageResponse;
import com.health.dto.request.UserProfileRequest;
import com.health.entity.UserProfile;
import org.springframework.http.ResponseEntity;

import com.health.dto.response.ProfileDetailsResponse;
import com.health.entity.User;
import com.health.models.ApiResponse;

/**
 * 
 */
public interface UserProfileService {
	
	public ApiResponse<ProfileDetailsResponse> createNewProfile(User user, UserProfileRequest userProfileRequest);
	public ApiResponse<ProfileDetailsResponse> findUserProfileById(Long userId);
	public ApiResponse<ProfileDetailsResponse> updateProfile(Long userId, UserProfileRequest userProfileRequest);

}
