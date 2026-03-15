/**
 * 
 */
package com.health.service;

import com.health.entity.UserProfileDetails;
import org.springframework.http.ResponseEntity;

import com.health.dto.request.ProfileDetailsRequest;
import com.health.dto.response.ProfileDetailsResponse;
import com.health.entity.UserRegistration;
import com.health.models.ApiResponse;

/**
 * 
 */
public interface UserProfileService {
	
	public ResponseEntity<ApiResponse<ProfileDetailsResponse>> createNewProfile(UserRegistration user,ProfileDetailsRequest profileDetailsRequest);
	public ApiResponse<UserProfileDetails> personalDetails(Long userId,UserProfileDetails userProfileDetails);
	public ApiResponse<UserProfileDetails> getUserProfileDetails(Long userId);

}
