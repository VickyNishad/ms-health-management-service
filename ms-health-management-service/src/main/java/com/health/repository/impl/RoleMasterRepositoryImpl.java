/**
 * 
 */
package com.health.repository.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.health.entity.RoleMaster;
import com.health.repository.RoleMasterRepository;
import com.health.repository.jpa.JPARoleMasterRepository;


/**
 * 
 */
@Component
public class RoleMasterRepositoryImpl implements RoleMasterRepository {
	
	@Autowired
	private JPARoleMasterRepository roleMasterRepository;

	@Override
	public Optional<RoleMaster> findById(Long id) {
		// TODO Auto-generated method stub
		Optional<RoleMaster> roleMaster = roleMasterRepository.findById(id);
		return roleMaster;
	}

	@Override
	public Optional<RoleMaster> findByRoleName(String roleName) {
		// TODO Auto-generated method stub
		Optional<RoleMaster> roleMaster = roleMasterRepository.findByRoleName(roleName);
		return roleMaster;
	}

}
