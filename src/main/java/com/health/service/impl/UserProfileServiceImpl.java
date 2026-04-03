/**
 *
 */
package com.health.service.impl;

import java.util.Optional;

import com.health.dto.request.UserProfileRequest;
import com.health.entity.User;
import com.health.entity.UserProfile;
import com.health.mappers.UserProfileMapper;
import com.health.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.dto.response.ProfileDetailsResponse;
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
    private UserRepository userRepository;


    private final UserProfileMapper userProfileMapper;

    @Autowired
    private KycStepService kycStepService;

    public UserProfileServiceImpl(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public ApiResponse<ProfileDetailsResponse> createNewProfile(User user, UserProfileRequest userProfileRequest) {
        // TODO Auto-generated method stub

        return ApiExecutionUtils.ApiExecutor.processRequest(user, req -> {
        }, () -> {
            Optional<UserProfile> optionalUserProfile = userProfileRepository.findByUserId(user.getId());
            if (optionalUserProfile.isPresent()) {
                UserProfile userProfile = optionalUserProfile.get();
                // need to set update mandatory data
                userProfileRepository.save(userProfile);
                // need to return here
                return userProfileMapper.toResponse(userProfile);
            }

            UserProfile profile = userProfileMapper.toEntity(userProfileRequest);
            profile = userProfileRepository.save(profile);

            kycStepService.addStep(user.getId(), 2L);

            return userProfileMapper.toResponse(profile);

        }, ApiResponse::success);

    }

    //	@Override
    public ApiResponse<UserProfile> personalDetails(Long userId, UserProfile userProfile) {

        return ApiExecutionUtils.ApiExecutor.processRequest(
                null,
                req -> {
                },
                () -> {
                    Optional<User> user = userRepository.findById(userId);
                    if (user.isEmpty()) {
                        throw new RuntimeException("User not found. Please create an account to proceed.");
                    }

                    Optional<UserProfile> optionalUserProfileDetails =
                            userProfileRepository.findByUserId(userId);

                    UserProfile profile;
                    if (optionalUserProfileDetails.isPresent()) {
                        // UPDATE
                        profile = optionalUserProfileDetails.get();

                    } else {
                        // INSERT
                        profile = userProfile;
                        profile.setUser(user.get());
                        kycStepService.addStep(userId, 3L);
                    }

                    return userProfileRepository.save(profile);

                },
                ApiResponse::success
        );
    }

    @Override
    public ApiResponse<ProfileDetailsResponse> findUserProfileById(Long userId) {

        return ApiExecutionUtils.ApiExecutor.processRequest(
                null,
                req -> {
                },
                () -> {
                    Optional<User> user = userRepository.findById(userId);
                    if (user.isEmpty()) {
                        throw new RuntimeException("User not found. Please create an account to proceed.");
                    }

                    Optional<UserProfile> optionalUserProfileDetails =
                            userProfileRepository.findByUserId(userId);
                    if (optionalUserProfileDetails.isEmpty()) {
                        throw new RuntimeException("User profile not found. Please create an account to proceed.");
                    }
                    UserProfile profile = null;
                    // UPDATE
                    profile = optionalUserProfileDetails.get();

                    return userProfileMapper.toResponse(profile);

                },
                ApiResponse::success
        );
    }

    /**
     *
     */
//	@Override
    public ApiResponse<ProfileDetailsResponse> createNewProfile(Long userId, UserProfileRequest userProfileRequest) {

        return ApiExecutionUtils.ApiExecutor.processRequest(null,
                req -> {
                },
                () -> {
                    Optional<User> optionalUserRegistration = userRepository.findById(userId);
                    if (optionalUserRegistration.isEmpty()) {
                        throw new RuntimeException("User not found. Please create an account to proceed.");
                    }

                    User user = optionalUserRegistration.get();
                    UserProfile userProfile = userProfileMapper.toEntity(userProfileRequest); // getProfileDetailsRequest(user, userProfileRequest);

                    Optional<UserProfile> optionalUserProfile = userProfileRepository.findByUserId(userId);
                    if (optionalUserProfile.isPresent()) {
                        userProfile.setId(optionalUserProfile.get().getId());
                    }
                    userProfile = userProfileRepository.save(userProfile);
                    return userProfileMapper.toResponse(userProfile);
                },
                ApiResponse::success);
    }

    /**
     *
     */
    @Override
    public ApiResponse<ProfileDetailsResponse> updateProfile(Long userId, UserProfileRequest userProfileRequest) {
        return ApiExecutionUtils.ApiExecutor.processRequest(null,
                req -> {
                },
                () -> {
                    Optional<User> optionalUser = userRepository.findById(userId);
                    if (optionalUser.isEmpty()) {
                        throw new RuntimeException("User not found. Please create an account to proceed.");
                    }
                    Optional<UserProfile> profileOptional = userProfileRepository.findByUserId(userId);
                    if (profileOptional.isEmpty()) {
                        throw new RuntimeException("User profile not found. Please create an account to proceed.");
                    }

                    User user = optionalUser.get();
                    UserProfile userProfile = userProfileMapper.toEntity(userProfileRequest);
                    userProfile.setId(profileOptional.get().getId());
                    userProfile = userProfileRepository.save(userProfile);

                    return userProfileMapper.toResponse(userProfile);
                },
                ApiResponse::success);
    }

}
