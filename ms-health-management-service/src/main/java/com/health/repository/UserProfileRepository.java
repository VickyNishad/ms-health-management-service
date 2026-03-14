/**
 * 
 */
package com.health.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.entity.UserProfileDetails;

/**
 * 
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileDetails, Long> {

	Optional<UserProfileDetails> findByUserId(Long userId);
}
