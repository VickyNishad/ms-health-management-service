package com.health.mappers;

import com.health.dto.request.UserProfileRequest;
import com.health.dto.response.ProfileDetailsResponse;
import com.health.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    public UserProfile toEntity(UserProfileRequest userProfileRequest) {
        UserProfile userProfile = new UserProfile();
        userProfile.setName(userProfileRequest.getName());
        userProfile.setMobileNumber(userProfileRequest.getMobileNumber());
        userProfile.setEmailId(userProfileRequest.getEmailId());
        userProfile.setGender(userProfileRequest.getGender());
        userProfile.setIsEmailVerified(userProfileRequest.getIsEmailVerified());
        userProfile.setIsMobileVerified(userProfileRequest.getIsMobileVerified());
        userProfile.setAge(userProfileRequest.getAge());
        userProfile.setProfilePicture(userProfileRequest.getProfilePicture());
        return userProfile;

    }

    public ProfileDetailsResponse toResponse(UserProfile userProfile) {
        ProfileDetailsResponse profileDetailsResponse = new ProfileDetailsResponse();
        profileDetailsResponse.setUserId(userProfile.getId());
        profileDetailsResponse.setName(userProfile.getName());
        profileDetailsResponse.setMobileNumber(userProfile.getMobileNumber());
        profileDetailsResponse.setEmailId(userProfile.getEmailId());
        profileDetailsResponse.setGender(userProfile.getGender());
        profileDetailsResponse.setIsEmailVerified(userProfile.getIsEmailVerified());
        profileDetailsResponse.setIsMobileVerified(userProfile.getIsMobileVerified());
        profileDetailsResponse.setAge(userProfile.getAge());
        profileDetailsResponse.setProfilePicture(userProfile.getProfilePicture());
        return profileDetailsResponse;
    }

}
