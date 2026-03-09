/**
 * 
 */
package com.health.repository;

import java.util.Optional;


import com.health.domain.model.UserRegistrationModel;
import com.health.entity.UserRegistration;

/**
 * 
 */
public interface UserRegistrationRepository {
	
	public Optional<UserRegistration> findByProviderLoginId(String providerLoginId);
	public UserRegistration save(UserRegistrationModel model);
	public Optional<UserRegistration> findById(Long id);

}
