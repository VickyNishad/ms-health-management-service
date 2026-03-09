/**
 * 
 */
package com.health.repository;

import java.util.Optional;

import com.health.entity.Clinic;



/**
 * 
 */
public interface ClinicRepository {

	public Clinic save(Clinic clinic);
	public Optional<Clinic> findById(Long clinicId);
}
