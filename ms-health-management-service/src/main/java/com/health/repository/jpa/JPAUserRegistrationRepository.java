/**
 * 
 */
package com.health.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.model.UserRegistration;



/**
 * 
 */
@Repository
public interface JPAUserRegistrationRepository extends JpaRepository<UserRegistration, Long> {
	Optional<UserRegistration> findByProviderLoginId(String providerLoginId);
}
