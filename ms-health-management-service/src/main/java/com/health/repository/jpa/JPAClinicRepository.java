/**
 * 
 */
package com.health.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.health.model.Clinic;



/**
 * 
 */
@Service
public interface JPAClinicRepository extends JpaRepository<Clinic, Long> {

}
