/**
 * 
 */
package com.health.controller;

import com.health.dto.MessageResponse;
import com.health.dto.request.UserProfileRequest;
import com.health.entity.UserProfile;
import com.health.models.ApiResponse;
import com.health.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/user")
public class UserProfileController {

	@Autowired
	private UserProfileService userProfileService;
	
	@GetMapping("/{userId}/profile")
	public ApiResponse<UserProfile> user(@PathVariable Long userId) {
		return userProfileService.getUserProfileDetails(userId);
	}

	@PostMapping("/update/{userId}/profile")
	public ApiResponse<MessageResponse> userUpdate(@PathVariable Long userId, @RequestBody UserProfileRequest userProfileRequest) {
		return userProfileService.updateProfile(userId, userProfileRequest);
	}

}
