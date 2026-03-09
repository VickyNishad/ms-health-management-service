/**
 * 
 */
package com.health.repository;

import java.util.Optional;

import com.health.domain.model.DoctorModel;
import com.health.entity.Doctor;

/**
 * 
 */
public interface DoctorRepository {
	Optional<Doctor> findById(Long id);
	Doctor save(DoctorModel model);
}
