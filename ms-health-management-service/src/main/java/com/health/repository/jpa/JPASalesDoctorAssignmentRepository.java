/**
 * 
 */
package com.health.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.entity.SalesDoctorAssignment;


/**
 * 
 */
@Repository
public interface JPASalesDoctorAssignmentRepository extends JpaRepository<SalesDoctorAssignment, Long> {

}
