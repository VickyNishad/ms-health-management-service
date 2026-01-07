/**
 * 
 */
package com.health.repository.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.health.model.Clinic;
import com.health.repository.ClinicRepository;
import com.health.repository.jpa.JPAClinicRepository;



/**
 * 
 */
@Component
public class ClinicRepositoryImpl implements ClinicRepository {
	
	@Autowired
	private JPAClinicRepository clinicRepository;

	@Override
	public Clinic save(Clinic clinic) {
		// TODO Auto-generated method stub
		clinicRepository.save(clinic);
		return clinic;
	}

	@Override
	public Optional<Clinic> findById(Long clinicId) {
		// TODO Auto-generated method stub
		return clinicRepository.findById(clinicId);
	}
}
