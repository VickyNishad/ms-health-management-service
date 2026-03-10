/**
 * 
 */
package com.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.entity.Doctor;



/**
 * 
 */
@Repository
public interface JPADoctorRepository extends JpaRepository<Doctor, Long> {

}
