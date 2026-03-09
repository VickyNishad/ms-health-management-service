/**
 * 
 */
package com.health.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.health.entity.Doctor;
import com.health.entity.DoctorClinic;
import com.health.repository.DoctorClinicRepository;
import com.health.repository.jpa.JPADoctorClinicRepository;


/**
 * 
 */
@Component
public class DoctorClinicRepositoryImpl implements DoctorClinicRepository {
	
	@Autowired
	private JPADoctorClinicRepository doctorClinicRepository;

	@Override
	public DoctorClinic save(DoctorClinic doctorClinic) {
		// TODO Auto-generated method stub
		doctorClinicRepository.save(doctorClinic);
		return doctorClinic;
	}

	@Override
	public List<DoctorClinic> findByDoctor(Doctor doctor) {
		// TODO Auto-generated method stub
		return doctorClinicRepository.findByDoctor(doctor);
	}

}
