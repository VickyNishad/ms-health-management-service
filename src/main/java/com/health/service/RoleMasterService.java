/**
 * 
 */
package com.health.service;

import java.util.List;

import com.health.entity.RoleMaster;
import com.health.dto.response.ApiResponse;

/**
 * 
 */
public interface RoleMasterService {

	public ApiResponse<List<RoleMaster>> findAll();
	public ApiResponse<RoleMaster> findById(Long roleId);
	public ApiResponse<RoleMaster> findByRole(String role);
	public ApiResponse<RoleMaster> findByUserId(Long userId);
}
