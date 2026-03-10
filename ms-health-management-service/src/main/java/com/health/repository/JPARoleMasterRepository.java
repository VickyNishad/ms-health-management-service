/**
 * 
 */
package com.health.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.health.entity.RoleMaster;



/**
 * 
 */
@Repository
public interface JPARoleMasterRepository extends JpaRepository<RoleMaster, Long> {

	Optional<RoleMaster> findByRoleName(String roleName);
}
