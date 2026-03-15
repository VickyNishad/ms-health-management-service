/**
 * 
 */
package com.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.health.entity.Clinic;



/**
 * 
 */
@Service
public interface ClinicRepository extends JpaRepository<Clinic, Long> {

}
