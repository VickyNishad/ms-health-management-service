/**
 * 
 */
package com.health.repository;

import java.util.Optional;

import com.health.entity.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 */
@Repository
public interface OTPMasterRepository extends JpaRepository<UserOtp, Long> {

	Optional<UserOtp> findTopByMobileNumberOrderByCreatedAtDesc(String mobileNumber);

	Optional<UserOtp> findTopByEmailIdOrderByCreatedAtDesc(String emailId);
	
	boolean existsByMobileNumberAndIsVerifiedTrue(String mobileNumber);

	boolean existsByEmailIdAndIsVerifiedTrue(String emailId);
}
