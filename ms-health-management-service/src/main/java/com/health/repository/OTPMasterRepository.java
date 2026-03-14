/**
 * 
 */
package com.health.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.entity.OtpMaster;

/**
 * 
 */
@Repository
public interface OTPMasterRepository extends JpaRepository<OtpMaster, Long> {

	Optional<OtpMaster> findTopByMobileNumberOrderByCreatedAtDesc(String mobileNumber);

	Optional<OtpMaster> findTopByEmailIdOrderByCreatedAtDesc(String emailId);
	
	boolean existsByMobileNumberAndIsVerifiedTrue(String mobileNumber);

	boolean existsByEmailIdAndIsVerifiedTrue(String emailId);
}
