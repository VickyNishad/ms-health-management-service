/**
 * 
 */
package com.health.repository.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.health.entity.SpecializationMaster;
import com.health.repository.SpecializationMasterRepository;
import com.health.repository.jpa.JPASpecializationMasterRepository;

/**
 * 
 */
@Component
public class SpecializationMasterRepositoryImpl implements SpecializationMasterRepository {
	
	@Autowired
	private JPASpecializationMasterRepository specializationMasterRepository;

	@Override
	public List<SpecializationMaster> findAllById(List<Long> id) {
		// TODO Auto-generated method stub
		return specializationMasterRepository.findAllById(id);
	}
}
