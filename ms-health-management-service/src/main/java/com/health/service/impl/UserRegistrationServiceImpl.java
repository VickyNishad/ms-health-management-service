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
import com.health.dto.response.RegisteredUserResponse;
import com.health.entity.RoleMaster;
import com.health.entity.UserRegistration;
import com.health.models.ApiResponse;
import com.health.repository.UserRegistrationRepository;
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

	@Override
	public ResponseEntity<ApiResponse<RegisteredUserResponse>> isRegister(String providerLofinId) {
		// TODO Auto-generated method stub
		RegisteredUserResponse registeredUserResponse = new RegisteredUserResponse();
		ApiResponse<RegisteredUserResponse> success = ApiExecutionUtils.ApiExecutor.processRequest(providerLofinId, req -> {
		}, () -> {
			Optional<UserRegistration> user = userRegistrationRepository.findByMobileNumber(providerLofinId);
			if(user.isPresent()) {
				UserRegistration userData = user.get();
				
				registeredUserResponse.setUserName(userData.getUserName());
				registeredUserResponse.setIsActive(userData.getIs_active());
				registeredUserResponse.setIsRegistered(userData.getIs_active());
				registeredUserResponse.setLoginType(userData.getLoginType());
				
				RoleMaster roleMaster = userData.getRole();
				
				registeredUserResponse.setRole(roleMaster.getRoleName());
				registeredUserResponse.setRoleId(roleMaster.getId());
				
				
			} else {
				throw new RuntimeException("User not found. Please create an account to proceed.");
			}
			return registeredUserResponse;
		}, ApiResponse::success);
		return new ResponseEntity<ApiResponse<RegisteredUserResponse>>(success, HttpStatus.OK);
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
				userRegistration.setUpdated_at(LocalDateTime.now());
				userRegistration.setUpdated_by(mobileNumber);
				
				userRegistrationRepository.save(userRegistration);
				return new MessageResponse("Your password has been reset successfully.");
			} else {
				throw new RuntimeException("We couldn’t find an account associated with this "+restPasswordRequest.getProviderLoginId()+".");
			}
		}, ApiResponse::success);
		return new ResponseEntity<ApiResponse<MessageResponse>>(success, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ApiResponse<MessageResponse>> register(ResetPasswordRequest restPasswordRequest) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public ResponseEntity<ApiResponse<RegisteredUserResponse>> isRegUser(String providerLofinId) {
//		// TODO Auto-generated method stub
//		RegisteredUserResponse isUser = new RegisteredUserResponse();
//		ApiResponse<RegisteredUserResponse> success = ApiExecutionUtils.ApiExecutor.processRequest(providerLofinId, req -> {
//		}, () -> {
//			Optional<UserRegistration> user = useRepositoryPort.findByProviderLoginId(providerLofinId);
//			if(user.isPresent()) {
//				// return message
//				isUser.setFullName(user.get().getFullName());
//				isUser.setIsActive(user.get().getIsActive());
//				isUser.setIsRegistered(true);
//				isUser.setLoginType(user.get().getLoginType());
//				isUser.setRole(user.get().getRole().getRoleName());
//			} else {
//				throw new RuntimeException("User not found. Please create an account to proceed.");
//			}
//			return isUser;
//		}, ApiResponse::success);
//		return new ResponseEntity<ApiResponse<RegisteredUserResponse>>(success, HttpStatus.OK);
//	}
//
//	@Override
//	public ResponseEntity<ApiResponse<MessageResponse>> resetPassword(ResetPasswordRequest restPasswordRequest) {
//		// TODO Auto-generated method stub
//		ApiResponse<MessageResponse> success = ApiExecutionUtils.ApiExecutor.processRequest(restPasswordRequest,
//				req -> {
//					String password = restPasswordRequest.getPassword();
//					String encryptPass = CommonUtils.encryptPassword(password);
//					String cnfPassword = restPasswordRequest.getConfirmPassword();
//					Boolean match = CommonUtils.matchPassword(cnfPassword, encryptPass);
//					if (!match) {
//						throw new RuntimeException("Passwords do not match. Please try again.");
//					}
//				}, () -> {
//					Optional<UserRegistration> optionalUser = useRepositoryPort.findByProviderLoginId(restPasswordRequest.getProviderLoginId());
//					if(optionalUser.isPresent()) {
//						// return message
//						UserRegistration userRegistration = optionalUser.get();
//						UserRegistrationModel model = new UserRegistrationModel();
//						model.setUserId(userRegistration.getUserId());
//						model.setContactNumber(userRegistration.getProviderLoginId());
//						model.setEmailId(userRegistration.getEmailId());
//						model.setFullName(userRegistration.getFullName());
//						model.setLoginType(userRegistration.getLoginType());
//						model.setPassword(CommonUtils.encryptPassword(restPasswordRequest.getPassword()));
//						model.setProfilePicture(userRegistration.getProfilePicture());
//						model.setProviderLoginId(userRegistration.getProviderLoginId());
//						model.setRole(userRegistration.getRole());
//						model.setSocialId(null);
//						useRepositoryPort.save(model);
//						return new MessageResponse("Your password has been reset successfully.");
//					} else {
//						throw new RuntimeException("We couldn’t find an account associated with this "+restPasswordRequest.getProviderLoginId()+".");
//					}
//				}, ApiResponse::success);
//		return new ResponseEntity<ApiResponse<MessageResponse>>(success, HttpStatus.OK);
//	}

}
