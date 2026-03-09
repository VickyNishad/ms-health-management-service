/**
 * 
 */
package com.health.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.entity.QualificationMaster;



/**
 * 
 */
@Repository
public interface JPAQualificationMasterRepository extends JpaRepository<QualificationMaster, Long> {

}
