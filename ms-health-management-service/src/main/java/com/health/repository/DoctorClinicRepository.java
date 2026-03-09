/**
 * 
 */
package com.health.repository;

import java.util.List;

import com.health.entity.Doctor;
import com.health.entity.DoctorClinic;



/**
 * 
 */
public interface DoctorClinicRepository {

	public DoctorClinic save(DoctorClinic doctorClinic);
	List<DoctorClinic> findByDoctor(Doctor doctor);
}
