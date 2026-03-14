/**
 * 
 */
package com.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.entity.KycStepMaster;

/**
 * 
 */
@Repository
public interface KycStepMasterRepository extends JpaRepository<KycStepMaster, Long> {

}
