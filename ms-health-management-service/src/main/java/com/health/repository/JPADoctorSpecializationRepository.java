/**
 * 
 */
package com.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.entity.DoctorSpecialization;


/**
 * 
 */
@Repository
public interface JPADoctorSpecializationRepository extends JpaRepository<DoctorSpecialization, Long>{

}
