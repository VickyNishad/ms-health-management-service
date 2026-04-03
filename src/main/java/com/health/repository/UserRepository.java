/**
 * 
 */
package com.health.repository;

import java.util.Optional;

import com.health.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByMobileNumber(String mobileNumber);

	Optional<User> findByEmailId(String emailId);

	Optional<User> findBySocialId(String socialId);

}
