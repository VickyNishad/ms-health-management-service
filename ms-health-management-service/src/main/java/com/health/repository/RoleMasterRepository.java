/**
 * 
 */
package com.health.repository;

import java.util.Optional;

import com.health.entity.RoleMaster;



/**
 * 
 */
public interface RoleMasterRepository {

	public Optional<RoleMaster> findById(Long id);
	public Optional<RoleMaster> findByRoleName(String roleName);
}
