/**
 * 
 */
package com.health.repository;

import java.util.List;

import com.health.model.Doctor;
import com.health.model.DoctorClinic;



/**
 * 
 */
public interface DoctorClinicRepository {

	public DoctorClinic save(DoctorClinic doctorClinic);
	List<DoctorClinic> findByDoctor(Doctor doctor);
}
