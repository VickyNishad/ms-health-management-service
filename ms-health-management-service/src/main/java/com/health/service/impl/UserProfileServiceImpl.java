/**
 * 
 */
package com.health.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.health.dto.request.ProfileDetailsRequest;
import com.health.dto.response.ProfileDetailsResponse;
import com.health.dto.response.UserRegisteredResponse;
import com.health.entity.RoleMaster;
import com.health.entity.UserProfileDetails;
import com.health.entity.UserRegistration;
import com.health.models.ApiResponse;
import com.health.repository.UserProfileRepository;
import com.health.service.KycStepService;
import com.health.service.UserProfileService;
import com.health.utility.ApiExecutionUtils;

/**
 * 
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private KycStepService kycStepService;

	@Override
	public ResponseEntity<ApiResponse<ProfileDetailsResponse>> createNewProfile(UserRegistration user,ProfileDetailsRequest profileDetailsRequest) {
		// TODO Auto-generated method stub
	
		ApiResponse<ProfileDetailsResponse> success = ApiExecutionUtils.ApiExecutor.processRequest(user, req -> {
		}, () -> {
			Optional<UserProfileDetails> optionalUserProfile = userProfileRepository.findByUserId(user.getId());
			ProfileDetailsResponse profileDetailsResponse = new ProfileDetailsResponse();
			if(optionalUserProfile.isPresent()) {
			    UserProfileDetails userProfile = optionalUserProfile.get();
			    // need to set update mandatory data
			    userProfileRepository.save(userProfile);
			   // need to return here
			    return profileDetailsResponse;
			}
			
			UserProfileDetails userProfileDetails = new UserProfileDetails();
			userProfileDetails.setName(profileDetailsRequest.getName());
			userProfileDetails.setMobileNumber(profileDetailsRequest.getMobileNumber());
			userProfileDetails.setUser(user);
			userProfileDetails.setEmailId(profileDetailsRequest.getEmailId());
			userProfileDetails.setGender(profileDetailsRequest.getGender());
			userProfileDetails.setIsEmailVerified(profileDetailsRequest.getIsEmailVerified());
			userProfileDetails.setIsMobileVerified(profileDetailsRequest.getIsMobileVerified());
			userProfileDetails.setAge(profileDetailsRequest.getAge());
			userProfileDetails.setProfilePicture(profileDetailsRequest.getProfilePicture());
			userProfileDetails.setCreatedAt(LocalDateTime.now());
			userProfileDetails.setCreatedBy(user.getId().toString());
			
			userProfileRepository.save(userProfileDetails);
			
			kycStepService.addStep(user.getId(), 2L);
			
			return profileDetailsResponse;
			
		}, ApiResponse::success);
		return new ResponseEntity<ApiResponse<ProfileDetailsResponse>>(success, HttpStatus.OK);

	}

	@Override
	public void personalDetails(Long profileId, Long userId) {
		// TODO Auto-generated method stub
		
	}

}
