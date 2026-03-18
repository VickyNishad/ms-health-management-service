/**
 * 
 */
package com.health.controller;

import com.health.dto.MessageResponse;
import com.health.dto.request.ProfileDetailsRequest;
import com.health.entity.UserProfileDetails;
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
	public ApiResponse<UserProfileDetails> user(@PathVariable Long userId) {
		return userProfileService.getUserProfileDetails(userId);
	}

	@PostMapping("/update/{userId}/profile")
	public ApiResponse<MessageResponse> userUpdate(@PathVariable Long userId, @RequestBody ProfileDetailsRequest profileDetailsRequest) {
		return userProfileService.updateProfile(userId,profileDetailsRequest);
	}

}
