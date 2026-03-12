/**
 * 
 */
package com.health.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.health.dto.request.LoginRequest;
import com.health.dto.response.UserResponse;
import com.health.entity.RoleMaster;
import com.health.entity.UserProfileDetails;
import com.health.entity.UserRegistration;
import com.health.models.ApiResponse;
import com.health.repository.RoleMasterRepository;
import com.health.repository.UserProfileRepository;
import com.health.repository.UserRegistrationRepository;
import com.health.service.AuthenticationService;
import com.health.utility.ApiExecutionUtils;
import com.health.utility.HealthUtils;

/**
 * 
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserRegistrationRepository userRegistrationRepository;
	
	@Autowired
	private RoleMasterRepository roleMasterRepository;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Override
	public ResponseEntity<ApiResponse<UserResponse>> autenticate(LoginRequest loginRequest) {
		// TODO Auto-generated method stub
		ApiResponse<UserResponse> success = ApiExecutionUtils.ApiExecutor.processRequest(loginRequest, req -> {
		}, () -> {
			
			Optional<UserRegistration> optionUser = userRegistrationRepository.findByMobileNumber(loginRequest.getProviderLoginId());
			if(!optionUser.isPresent()) {
				throw new RuntimeException("User not found. Please create an account to proceed.");
			}
			UserResponse userResponse = new UserResponse();
			UserRegistration user = optionUser.get();
			if(loginRequest.getRoleId() == 4) {
				userResponse = userAuthentication(user,loginRequest);
			} else {
				userResponse = employeeAuthentication(user,loginRequest);	
			}
			
			return userResponse;
		}, ApiResponse::success);

		return new ResponseEntity<ApiResponse<UserResponse>>(success , HttpStatus.OK);
	}
	
	private UserResponse userAuthentication(UserRegistration user , LoginRequest loginRequest ) {
		String role = user.getRole().getRoleName();
		
		Optional<RoleMaster> optional = roleMasterRepository.findById(loginRequest.getRoleId());
		RoleMaster roleMaster = optional.get();
		String requestRole = roleMaster.getRoleName();
		
		if(user.getRole().getId() != loginRequest.getRoleId()) {
			
			throw new RuntimeException("Role mismatch. You are registered as a "+role+" and cannot login as a "+requestRole+".");
		}
		
		String pass = loginRequest.getPassword();
		String regPass = user.getPassword();
		if(!HealthUtils.matchPassword(pass, regPass)) {
			throw new RuntimeException("Invalid password. Please try again.");
		}
		
		Optional<UserProfileDetails> optionalUserProfile = userProfileRepository.findByUserId(user.getId());
		
		return userResponse(optionalUserProfile.get(),user);

	}
	
	private UserResponse employeeAuthentication(UserRegistration user , LoginRequest loginRequest) {
		String role = user.getRole().getRoleName();
		
		Optional<RoleMaster> optional = roleMasterRepository.findById(loginRequest.getRoleId());
		RoleMaster roleMaster = optional.get();
		String requestRole = roleMaster.getRoleName();
		
		if(user.getRole().getId() == 4) {
			
			throw new RuntimeException("Role mismatch. You are registered as a "+role+" and cannot login as a "+requestRole+".");
		}
		
		String pass = loginRequest.getPassword();
		String regPass = user.getPassword();
		if(!HealthUtils.matchPassword(pass, regPass)) {
			throw new RuntimeException("Invalid password. Please try again.");
		}
		
		Optional<UserProfileDetails> optionalUserProfile = userProfileRepository.findByUserId(user.getId());
		
		return userResponse(optionalUserProfile.get(),user);
	}

	private UserResponse userResponse(UserProfileDetails userProfileDetails , UserRegistration user) {
		
		UserResponse userResponse = new UserResponse();
		userResponse.setId(userProfileDetails.getId());
		userResponse.setUserId(user.getId());
		userResponse.setIsRegistered(true);
		userResponse.setRoleId(user.getRole().getId());
		userResponse.setRole(user.getRole().getRoleName());
		userResponse.setUserName(user.getUserName());
		userResponse.setIsActive(true);
		userResponse.setLoginType(user.getLoginType());
		
		return userResponse;
		
	}
}
