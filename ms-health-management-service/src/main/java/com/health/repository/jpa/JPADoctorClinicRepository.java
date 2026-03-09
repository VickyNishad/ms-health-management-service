/**
 * 
 */
package com.health.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.entity.Doctor;
import com.health.entity.DoctorClinic;



/**
 * 
 */
@Repository
public interface JPADoctorClinicRepository extends JpaRepository<DoctorClinic, Long> {
	List<DoctorClinic> findByDoctor(Doctor doctor);
}
