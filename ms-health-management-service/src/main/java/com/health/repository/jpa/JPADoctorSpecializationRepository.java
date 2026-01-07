/**
 * 
 */
package com.health.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.model.DoctorSpecialization;


/**
 * 
 */
@Repository
public interface JPADoctorSpecializationRepository extends JpaRepository<DoctorSpecialization, Long>{

}
