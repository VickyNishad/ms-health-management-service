/**
 * 
 */
package com.health.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.health.entity.DoctorLeave;

/**
 * 
 */
@Repository
public interface JPADoctorLeaveRepository extends JpaRepository<DoctorLeave, Long> {


}
