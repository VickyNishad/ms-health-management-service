/**
 * 
 */
package com.health.repository;

import com.health.dto.response.PatientDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.entity.Patient;

import java.util.List;
import java.util.Optional;


/**
 * 
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findAllByUserId(Long userId);
    List<Patient> findAllByUserIdAndIsActiveTrue(Long userId);
    Optional<Patient> findByUserIdAndRelationAndName(Long userId, String relation, String name);
}
