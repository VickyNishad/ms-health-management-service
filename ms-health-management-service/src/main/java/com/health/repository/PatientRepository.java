/**
 * 
 */
package com.health.repository;

import java.util.List;
import java.util.Optional;


import com.health.domain.model.PatientModel;
import com.health.entity.Patient;

/**
 * 
 */
public interface PatientRepository {
	
	Optional<Patient> findById(Long id);
	Patient save(PatientModel model);
	void deleteById(Long patientId);
	List<Patient> findByUser_UserIdAndIsActive(Long userId, Boolean isActive);

}
