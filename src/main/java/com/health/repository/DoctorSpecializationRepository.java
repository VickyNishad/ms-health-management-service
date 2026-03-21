/**
 * 
 */
package com.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.entity.DoctorSpecialization;

import java.util.List;


/**
 * 
 */
@Repository
public interface DoctorSpecializationRepository extends JpaRepository<DoctorSpecialization, Long>{
    void  deleteByDoctorId(Long doctorId);

    List<DoctorSpecialization> findByDoctorId(Long doctorId);
}
