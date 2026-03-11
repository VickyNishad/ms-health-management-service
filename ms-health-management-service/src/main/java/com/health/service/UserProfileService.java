/**
 * 
 */
package com.health.service;

import com.health.dto.request.ProfileDetailsRequest;
import com.health.entity.UserRegistration;

/**
 * 
 */
public interface UserProfileService {
	
	public void createNewProfile(UserRegistration user,ProfileDetailsRequest profileDetailsRequest);
	public void personalDetails(Long profileId,Long userId);

}
