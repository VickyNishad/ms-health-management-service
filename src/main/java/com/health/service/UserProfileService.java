/**
 * 
 */
package com.health.service;

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
	public void personalDetails(Long profileId,Long userId);

}
