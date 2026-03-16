/**
 * 
 */
package com.health.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import com.health.dto.MessageResponse;
import com.health.repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.health.dto.request.ProfileDetailsRequest;
import com.health.dto.response.ProfileDetailsResponse;
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
	private UserRegistrationRepository userRegistrationRepository;
	
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
			    return getProfileDetailsResponse(userProfile);
			}
			
			UserProfileDetails userProfileDetails = getProfileDetailsRequest(user, profileDetailsRequest);
			userProfileRepository.save(userProfileDetails);
			kycStepService.addStep(user.getId(), 2L);

			return getProfileDetailsResponse(userProfileDetails);
			
		}, ApiResponse::success);
		return new ResponseEntity<>(success, HttpStatus.OK);

	}

	@Override
	public ApiResponse<UserProfileDetails> personalDetails(Long userId, UserProfileDetails userProfileDetails) {

        return ApiExecutionUtils.ApiExecutor.processRequest(
                null,
                req -> {},
                () -> {
                    Optional<UserRegistration> user = userRegistrationRepository.findById(userId);
                    if(user.isEmpty()) {
                        throw new RuntimeException("User not found. Please create an account to proceed.");
                    }

                    Optional<UserProfileDetails> optionalUserProfileDetails =
                            userProfileRepository.findByUserId(userId);

                    UserProfileDetails profile;
                    if (optionalUserProfileDetails.isPresent()) {
                        // UPDATE
                        profile = optionalUserProfileDetails.get();

                    } else {
                        // INSERT
                        profile = userProfileDetails;
                        profile.setUser(user.get());
                        kycStepService.addStep(userId,3L);
                    }

                    return userProfileRepository.save(profile);

                },
                ApiResponse::success
        );
	}

	/**
	 */
	@Override
	public ApiResponse<UserProfileDetails> getUserProfileDetails(Long userId) {

		return ApiExecutionUtils.ApiExecutor.processRequest(
				null,
				req -> {},
				() -> {
					Optional<UserRegistration> user = userRegistrationRepository.findById(userId);
					if(user.isEmpty()) {
						throw new RuntimeException("User not found. Please create an account to proceed.");
					}

					Optional<UserProfileDetails> optionalUserProfileDetails =
							userProfileRepository.findByUserId(userId);
					if (optionalUserProfileDetails.isEmpty()){
						throw new RuntimeException("User profile not found. Please create an account to proceed.");
					}
					UserProfileDetails profile = null;
                    // UPDATE
                    profile = optionalUserProfileDetails.get();

                    return profile;

				},
				ApiResponse::success
		);
    }

	/**
	 */
	@Override
	public ApiResponse<ProfileDetailsResponse> createNewProfile(Long userId, ProfileDetailsRequest profileDetailsRequest) {

       return ApiExecutionUtils.ApiExecutor.processRequest(null,
                req -> {
                },
                () -> {
                    Optional<UserRegistration> optionalUserRegistration = userRegistrationRepository.findById(userId);
                    if (optionalUserRegistration.isEmpty()) {
                        throw new RuntimeException("User not found. Please create an account to proceed.");
                    }

					UserRegistration user = optionalUserRegistration.get();
					UserProfileDetails userProfileDetails = getProfileDetailsRequest(user, profileDetailsRequest);

					Optional<UserProfileDetails> optionalUserProfile = userProfileRepository.findByUserId(userId);
					if(optionalUserProfile.isPresent()) {
						userProfileDetails.setId(optionalUserProfile.get().getId());
					}
                    userProfileDetails = userProfileRepository.save(userProfileDetails);
                    return getProfileDetailsResponse(userProfileDetails);
                },
                ApiResponse::success);
	}

	/**
	 */
	@Override
	public ApiResponse<MessageResponse> updateProfile(Long userId, ProfileDetailsRequest profileDetailsRequest) {
		return ApiExecutionUtils.ApiExecutor.processRequest(null,
				req -> {
				},
				() -> {
					Optional<UserRegistration> optionalUserRegistration = userRegistrationRepository.findById(userId);
					if (optionalUserRegistration.isEmpty()) {
						throw new RuntimeException("User not found. Please create an account to proceed.");
					}
					Optional<UserProfileDetails> optionalUserProfile = userProfileRepository.findByUserId(userId);
					if(optionalUserProfile.isEmpty()) {
						throw new RuntimeException("User profile not found. Please create an account to proceed.");
					}

					UserRegistration user = optionalUserRegistration.get();
					UserProfileDetails userProfileDetails = getProfileDetailsRequest(user, profileDetailsRequest);
					userProfileDetails.setId(optionalUserProfile.get().getId());
					userProfileDetails = userProfileRepository.save(userProfileDetails);

					return new MessageResponse("Profile updated successfully");
				},
				ApiResponse::success);
	}

	private static UserProfileDetails getProfileDetailsRequest(UserRegistration user,ProfileDetailsRequest profileDetailsRequest) {
		profileDetailsRequest.setName(profileDetailsRequest.getName());
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
		return userProfileDetails;
	}

	private static ProfileDetailsResponse getProfileDetailsResponse(UserProfileDetails userProfileDetails) {
		ProfileDetailsResponse profileDetailsResponse = new ProfileDetailsResponse();
		profileDetailsResponse.setId(userProfileDetails.getId());
		profileDetailsResponse.setName(userProfileDetails.getName());
		profileDetailsResponse.setMobileNumber(userProfileDetails.getMobileNumber());
		profileDetailsResponse.setEmailId(userProfileDetails.getEmailId());
		profileDetailsResponse.setGender(userProfileDetails.getGender());
		profileDetailsResponse.setIsEmailVerified(userProfileDetails.getIsEmailVerified());
		profileDetailsResponse.setIsMobileVerified(userProfileDetails.getIsMobileVerified());
		profileDetailsResponse.setAge(userProfileDetails.getAge());
		profileDetailsResponse.setProfilePicture(userProfileDetails.getProfilePicture());
		return profileDetailsResponse;
	}

}
