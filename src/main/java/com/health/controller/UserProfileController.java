/**
 * 
 */
package com.health.controller;

import com.health.dto.request.UserProfileRequest;
import com.health.dto.response.ProfileDetailsResponse;
import com.health.dto.response.ApiResponse;
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
	public ApiResponse<ProfileDetailsResponse> user(@PathVariable Long userId) {
		return userProfileService.findUserProfileById(userId);
	}

	@PostMapping("/update/{userId}/profile")
	public ApiResponse<ProfileDetailsResponse> userUpdate(@PathVariable Long userId, @RequestBody UserProfileRequest userProfileRequest) {
		return userProfileService.updateProfile(userId, userProfileRequest);
	}

}
