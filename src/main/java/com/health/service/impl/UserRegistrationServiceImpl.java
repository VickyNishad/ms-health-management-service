/**
 * 
 */
package com.health.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.health.dto.MessageResponse;
import com.health.dto.ResetPasswordRequest;
import com.health.dto.request.ProfileDetailsRequest;
import com.health.dto.request.UserRegistrationRequest;
import com.health.dto.response.ProfileDetailsResponse;
import com.health.dto.response.UserRegisteredResponse;
import com.health.dto.response.UserResponse;
import com.health.entity.RoleMaster;
import com.health.entity.UserRegistration;
import com.health.models.ApiResponse;
import com.health.repository.RoleMasterRepository;
import com.health.repository.UserRegistrationRepository;
import com.health.service.KycStepService;
import com.health.service.OTPService;
import com.health.service.UserProfileService;
import com.health.service.UserRegistrationService;
import com.health.utility.ApiExecutionUtils;
import com.health.utility.HealthUtils;

/**
 * 
 */
@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
	
	@Autowired
	private UserRegistrationRepository userRegistrationRepository;
	
	@Autowired
	private RoleMasterRepository roleMasterRepository;
	
	@Autowired
	private KycStepService kycStepService; 
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private OTPService otpService;


	@Override
	public ResponseEntity<ApiResponse<UserRegisteredResponse>> isRegister(String providerLofinId) {
		// TODO Auto-generated method stub
		UserRegisteredResponse userRegisteredResponse = new UserRegisteredResponse();
		ApiResponse<UserRegisteredResponse> success = ApiExecutionUtils.ApiExecutor.processRequest(providerLofinId, req -> {
		}, () -> {
			Optional<UserRegistration> user = userRegistrationRepository.findByMobileNumber(providerLofinId);
			if(user.isPresent()) {
				UserRegistration userData = user.get();
				
				userRegisteredResponse.setUserName(userData.getUserName());
				userRegisteredResponse.setIsActive(userData.getIsActive());
				userRegisteredResponse.setIsRegistered(userData.getIsActive());
				userRegisteredResponse.setLoginType(userData.getLoginType());
				
				RoleMaster roleMaster = userData.getRole();
				
				userRegisteredResponse.setRole(roleMaster.getRoleName());
				userRegisteredResponse.setRoleId(roleMaster.getId());

			} else {
				throw new RuntimeException("User not found. Please create an account to proceed.");
			}
			return userRegisteredResponse;
		}, ApiResponse::success);
		return new ResponseEntity<ApiResponse<UserRegisteredResponse>>(success, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ApiResponse<MessageResponse>> resetPassword(ResetPasswordRequest restPasswordRequest) {
		// TODO Auto-generated method stub
		ApiResponse<MessageResponse> success = ApiExecutionUtils.ApiExecutor.processRequest(restPasswordRequest,
		req -> {
			
			String password = restPasswordRequest.getPassword();
			String encryptPass = HealthUtils.encryptPassword(password);
			String cnfPassword = restPasswordRequest.getConfirmPassword();
			Boolean match = HealthUtils.matchPassword(cnfPassword, encryptPass);
			
			if (!match) {
				throw new RuntimeException("Passwords do not match. Please try again.");
			}
			
		}, () -> {
			
			String mobileNumber = restPasswordRequest.getProviderLoginId();
			String password = restPasswordRequest.getPassword();
			String encryptPass = HealthUtils.encryptPassword(password);
			
			Optional<UserRegistration> user = userRegistrationRepository.findByMobileNumber(mobileNumber);
			if(user.isPresent()) {
				// return message
				UserRegistration userRegistration = user.get();
				userRegistration.setPassword(encryptPass);
				userRegistration.setUpdatedAt(LocalDateTime.now());
				userRegistration.setUpdatedBy(mobileNumber);
				
				userRegistrationRepository.save(userRegistration);
				return new MessageResponse("Your password has been reset successfully.");
			} else {
				throw new RuntimeException("We couldn’t find an account associated with this "+restPasswordRequest.getProviderLoginId()+".");
			}
		}, ApiResponse::success);
		return new ResponseEntity<ApiResponse<MessageResponse>>(success, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ApiResponse<UserResponse>> register(UserRegistrationRequest userRegistrationRequest) {
		// TODO Auto-generated method stub
	
		ApiResponse<UserResponse> success = ApiExecutionUtils.ApiExecutor.processRequest(userRegistrationRequest,
				req -> {

				}, () -> {
					UserResponse userRes = new UserResponse();
					
					Optional<UserRegistration> user = userRegistrationRepository.findByMobileNumber(userRegistrationRequest.getMobileNumber());
					if(user.isPresent()) {
						throw new RuntimeException("You already have an account. Please log in to continue.");						
					}
					
					boolean isPresent =  otpService.existsByMobileNumber(userRegistrationRequest.getMobileNumber());
					if(!isPresent) {
						throw new RuntimeException("Your mobile number is not verified. Please verify the OTP first, then continue to register.");
					}
					
					if (userRegistrationRequest.getRoleId() == 4) {
						userRes = userRegistration( userRegistrationRequest ,isPresent);
					} else if (userRegistrationRequest.getRoleId() == 3) {
						userRes = doctorRegistration( userRegistrationRequest , isPresent);
					} else {
						
					}
					return userRes;
				}, ApiResponse::success);
		return new ResponseEntity<ApiResponse<UserResponse>>(success, HttpStatus.OK);
	}
	
	private UserResponse userRegistration(UserRegistrationRequest userRegistrationRequest,boolean isPresent) {
		Long roleId = (long) userRegistrationRequest.getRoleId();
		
		Optional<RoleMaster> optionalRoleMaster = roleMasterRepository.findById(roleId);
		RoleMaster roleMaster = optionalRoleMaster.get();
		
		String password = userRegistrationRequest.getPassword();
		String ecnryptPassword = HealthUtils.encryptPassword(password);
		
		userRegistrationRequest.setPassword(ecnryptPassword);
		
		UserRegistration userRegistration = userRegistration(userRegistrationRequest,roleMaster);
		userRegistration = userRegistrationRepository.save(userRegistration);
		
		/**
		 * Mark Registration step is completed
		 */
		Long userId = userRegistration.getId();
		Long stepId = 1L;
		kycStepService.addStep(userId, stepId);
		
		ProfileDetailsResponse profileDetailsResponse = createNewProfile(userRegistration,isPresent);
		
		return userResponse(userRegistration,profileDetailsResponse);
	}
	
	private UserResponse doctorRegistration(UserRegistrationRequest userRegistrationRequest,boolean isPresent) {
		Long roleId = (long) userRegistrationRequest.getRoleId();

		Optional<RoleMaster> optionalRoleMaster = roleMasterRepository.findById(roleId);
		RoleMaster roleMaster = optionalRoleMaster.get();

		String password = userRegistrationRequest.getPassword();
		String ecnryptPassword = HealthUtils.encryptPassword(password);

		userRegistrationRequest.setPassword(ecnryptPassword);

		UserRegistration userRegistration = userRegistration(userRegistrationRequest, roleMaster);
		userRegistration = userRegistrationRepository.save(userRegistration);

		/**
		 * Mark Registration step is completed
		 */
		Long userId = userRegistration.getId();
		Long stepId = 1L;
		kycStepService.addStep(userId, stepId);

		ProfileDetailsResponse profileDetailsResponse = createNewProfile(userRegistration, isPresent);

		return userResponse(userRegistration, profileDetailsResponse);
	}

	private void employeeRegistration(UserRegistrationRequest userRegistrationRequest) {
		System.out.println("Employee Registration Request");
	}
		
	private UserRegistration userRegistration(UserRegistrationRequest userRegistrationRequest,RoleMaster roleMaster) {
		
		UserRegistration userRegistration = new UserRegistration();
		userRegistration.setMobileNumber(userRegistrationRequest.getMobileNumber());
		userRegistration.setUserName(userRegistrationRequest.getUserName());
		userRegistration.setEmailId(userRegistrationRequest.getEmailId());
		userRegistration.setEmpCode(userRegistrationRequest.getEmpCode());
		userRegistration.setLoginType(userRegistrationRequest.getLoginType().name());
		userRegistration.setPassword(userRegistrationRequest.getPassword());
		userRegistration.setRole(roleMaster);
		userRegistration.setSocialId(userRegistrationRequest.getSocialId());
		userRegistration.setCreatedAt(LocalDateTime.now());
		userRegistration.setCreatedBy("BACKEND");
		userRegistration.setIsActive(true);
		return userRegistration;
	}
	
	private UserResponse userResponse(UserRegistration userRegistration,ProfileDetailsResponse profileDetailsResponse) {

		UserResponse userResponse = new UserResponse();
		userResponse.setId(profileDetailsResponse.getId());
		userResponse.setUserId(userRegistration.getId());
		userResponse.setIsRegistered(true);
		userResponse.setRoleId(userRegistration.getRole().getId());
		userResponse.setRole(userRegistration.getRole().getRoleName());
		userResponse.setUserName(userRegistration.getUserName());
		userResponse.setIsActive(true);
		return userResponse;
	}

	private ProfileDetailsResponse createNewProfile(UserRegistration userRegistration,boolean isMobileVerified) {
		
		ProfileDetailsRequest profileDetailsRequest = new ProfileDetailsRequest();
		profileDetailsRequest.setName(userRegistration.getUserName());
		profileDetailsRequest.setMobileNumber(userRegistration.getMobileNumber());
		profileDetailsRequest.setIsMobileVerified(isMobileVerified);
		
		ResponseEntity<ApiResponse<ProfileDetailsResponse>> apiResponseEntity = userProfileService.createNewProfile(userRegistration, profileDetailsRequest);
		ApiResponse<ProfileDetailsResponse> apiResponse = apiResponseEntity.getBody();
		ProfileDetailsResponse profileDetailsResponse = apiResponse.getData();
		
		return profileDetailsResponse;
	}
}
