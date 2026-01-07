/**
 * 
 */
package com.health.repository;


import java.util.List;

import com.health.model.SpecializationMaster;



/**
 * 
 */
public interface SpecializationMasterRepository {

	List<SpecializationMaster> findAllById(List<Long> id);
}
