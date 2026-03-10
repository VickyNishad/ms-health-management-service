/**
 * 
 */
package com.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.entity.DoctorQualification;



/**
 * 
 */
@Repository
public interface JPADoctorQualificationRepository extends JpaRepository<DoctorQualification, Long> {

}
