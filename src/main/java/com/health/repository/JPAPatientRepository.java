/**
 * 
 */
package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.entity.Patient;



/**
 * 
 */
@Repository
public interface JPAPatientRepository extends JpaRepository<Patient, Long> {

}
