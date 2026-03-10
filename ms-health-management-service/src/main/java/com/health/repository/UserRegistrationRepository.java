/**
 * 
 */
package com.health.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.entity.UserRegistration;

/**
 * 
 */
@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long> {
	
	Optional<UserRegistration> findByMobileNumber(String mobileNumber);

	Optional<UserRegistration> findByEmailId(String mobileNumber);

	Optional<UserRegistration> findBysocialId(String mobileNumber);

}
