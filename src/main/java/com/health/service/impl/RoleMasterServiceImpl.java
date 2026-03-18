/**
 * 
 */
package com.health.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.health.entity.RoleMaster;
import com.health.entity.UserRegistration;
import com.health.models.ApiResponse;
import com.health.repository.RoleMasterRepository;
import com.health.repository.UserRegistrationRepository;
import com.health.service.RoleMasterService;
import com.health.utility.ApiExecutionUtils;

/**
 * 
 */
@Service
public class RoleMasterServiceImpl implements RoleMasterService {
	
	@Autowired
	private RoleMasterRepository roleMasterRepository;
	
	@Autowired
	private UserRegistrationRepository userRegistrationRepository;

	@Override
	public ResponseEntity<ApiResponse<List<RoleMaster>>> findAll() {
		// TODO Auto-generated method stub
		ApiResponse<List<RoleMaster>> success = ApiExecutionUtils.ApiExecutor.processRequest(null, req -> {
		}, () -> {
            return roleMasterRepository.findAll();
		}, ApiResponse::success);
		return new ResponseEntity<>(success, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ApiResponse<List<RoleMaster>>> findByUserId(Long userId) {
		// TODO Auto-generated method stub
		
		ApiResponse<List<RoleMaster>> success = ApiExecutionUtils.ApiExecutor.processRequest(userId, req -> {
		}, () -> {
			Optional<UserRegistration> user = userRegistrationRepository.findById(userId);
			if(user.isPresent()) {
				throw new RuntimeException("User not found. Please create an account to proceed.");
			}
            return roleMasterRepository.findAll();
		}, ApiResponse::success);
		return new ResponseEntity<>(success, HttpStatus.OK);
	}

}
