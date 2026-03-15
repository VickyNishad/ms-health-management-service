/**
 * 
 */
package com.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.entity.QualificationMaster;



/**
 * 
 */
@Repository
public interface QualificationMasterRepository extends JpaRepository<QualificationMaster, Long> {

}
