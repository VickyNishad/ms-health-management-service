/**
 * 
 */
package com.health.repository;


import java.util.List;

import com.health.entity.SpecializationMaster;



/**
 * 
 */
public interface SpecializationMasterRepository {

	List<SpecializationMaster> findAllById(List<Long> id);
}
