/**
 * 
 */
package com.health.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.entity.SpecializationMaster;


/**
 * 
 */
@Repository
public interface JPASpecializationMasterRepository extends JpaRepository<SpecializationMaster, Long>{

}
