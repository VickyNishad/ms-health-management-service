/**
 * 
 */
package com.health.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.model.Patient;



/**
 * 
 */
@Repository
public interface JPAPatientRepository extends JpaRepository<Patient, Long> {

	List<Patient> findByUser_UserIdAndIsActive(Long userId, Boolean isActive);
}
