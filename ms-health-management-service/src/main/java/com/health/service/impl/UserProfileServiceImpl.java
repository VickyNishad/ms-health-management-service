/**
 * 
 */
package com.health.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.dto.request.ProfileDetailsRequest;
import com.health.entity.UserProfileDetails;
import com.health.entity.UserRegistration;
import com.health.repository.UserProfileRepository;
import com.health.service.KycStepService;
import com.health.service.UserProfileService;

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
	public void createNewProfile(UserRegistration user,ProfileDetailsRequest profileDetailsRequest) {
		// TODO Auto-generated method stub
		
		Optional<UserProfileDetails> optionalUserProfile = userProfileRepository.findByUserId(user.getId());

		if(optionalUserProfile.isPresent()) {
		    UserProfileDetails userProfile = optionalUserProfile.get();
		    
		    // need to set update mandatory data
		    userProfileRepository.save(userProfile);
		   // need to return here
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
		
		
	}

	@Override
	public void personalDetails(Long profileId, Long userId) {
		// TODO Auto-generated method stub
		
	}

}
