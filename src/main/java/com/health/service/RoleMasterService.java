/**
 * 
 */
package com.health.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.health.entity.RoleMaster;
import com.health.models.ApiResponse;

/**
 * 
 */
public interface RoleMasterService {

	public ResponseEntity<ApiResponse<List<RoleMaster>>> findAll();
	public ResponseEntity<ApiResponse<List<RoleMaster>>> findByUserId(Long userId);
}
