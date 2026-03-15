/**
 * 
 */
package com.health.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.health.dto.response.KycStepResponse;
import com.health.entity.KycStepMaster;
import com.health.entity.KycStepStatus;
import com.health.entity.RoleMaster;
import com.health.entity.UserRegistration;
import com.health.models.ApiResponse;
import com.health.repository.KycStepMasterRepository;
import com.health.repository.KycStepStatusReposotory;
import com.health.repository.UserRegistrationRepository;
import com.health.service.KycStepService;
import com.health.utility.ApiExecutionUtils;

/**
 * 
 */
@Service
public class KycStepServiceImpl implements KycStepService {
	
	@Autowired
	private KycStepMasterRepository kycStepMasterRepository;
	
	@Autowired
	private KycStepStatusReposotory kycStepStatusReposotory;
	
	@Autowired
	private UserRegistrationRepository userRegistrationRepository;

	@Override
	public void addStep(Long userId, Long stepId) {
		// TODO Auto-generated method stub
		
		Optional<UserRegistration> userOptional = userRegistrationRepository.findById(userId);
		if(userOptional.isPresent()) {
			
		}
		Optional<KycStepMaster> kycSteps = kycStepMasterRepository.findById(stepId);
		
		KycStepStatus kycStepStatus = new KycStepStatus();
		kycStepStatus.setIsCompleted(true);
		kycStepStatus.setUser(userOptional.get());
		kycStepStatus.setStep(kycSteps.get());
		kycStepStatus.setCreatedAt(LocalDateTime.now());
		kycStepStatus.setCreatedBy(userId.toString());
		kycStepStatus.setUpdatedAt(LocalDateTime.now());
		kycStepStatus.setUpdatedBy(userId.toString());
		
		kycStepStatusReposotory.save(kycStepStatus);
	}

	@Override
	public void updateStep(Long userId, Long stepId, Long kycStepId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResponseEntity<ApiResponse<List<KycStepResponse>>> allSteps(Long userId) {

		
		ApiResponse<List<KycStepResponse>> success = ApiExecutionUtils.ApiExecutor.processRequest(userId, req -> {
		}, () -> {
			Optional<UserRegistration> user = userRegistrationRepository.findById(userId);
			if(!user.isPresent()) {
				throw new RuntimeException("User not found. Please create an account to proceed.");
			}
			
			UserRegistration userData = user.get();
			RoleMaster roleMaster = userData.getRole();
			Long roleId = roleMaster.getId();
			
			List<KycStepResponse> kycStepResponses = new ArrayList<>();
			List<KycStepStatus> kycStepStatus = kycStepStatusReposotory.findByUser_Id(userId);
			
			switch (roleMaster.getRoleName()) {
			case "DOCTOR" :
				kycStepResponses = doctorSteps(userId,roleId,kycStepStatus);
				break;
				
			default:
				kycStepResponses = userSteps(userId,roleId,kycStepStatus);
				break;
			}

			return kycStepResponses;
		}, ApiResponse::success);
		return new ResponseEntity<ApiResponse<List<KycStepResponse>>>(success, HttpStatus.OK);
	}
	
	private List<KycStepResponse> userSteps(Long userId, Long roleId, List<KycStepStatus> kycStepStatus) {

	    List<KycStepResponse> kycStepResponses = new ArrayList<>();
	    List<KycStepMaster> kycSteps = kycStepMasterRepository.findAll();

	    for (KycStepMaster stepMaster : kycSteps) {

	        KycStepResponse response = new KycStepResponse();
	        boolean isCompleted = false;
	        Integer kycStepId = 0;

	        for (KycStepStatus stepStatus : kycStepStatus) {

	            if (stepMaster.getId().equals(stepStatus.getStep().getId())) {
	                isCompleted = stepStatus.getIsCompleted();
	                kycStepId = stepStatus.getId().intValue();
	                break;
	            }
	        }

	        response.setIsCompleted(isCompleted);
	        response.setKycStepId(kycStepId);
	        response.setStepId(stepMaster.getId().intValue());
	        response.setStepName(stepMaster.getStepName());
	        response.setStepOrder(stepMaster.getStepOrder());
	        response.setUserId(userId);

	        if (stepMaster.getId() == 1 || stepMaster.getId() == 2 || stepMaster.getId() == 3) {
	            kycStepResponses.add(response);
	        }
	    }

	    return kycStepResponses;
	}
	
	private List<KycStepResponse> doctorSteps(Long userId,Long roleId,List<KycStepStatus> kycStepStatus) {
	    List<KycStepResponse> kycStepResponses = new ArrayList<>();
	    List<KycStepMaster> kycSteps = kycStepMasterRepository.findAll();

	    for (KycStepMaster stepMaster : kycSteps) {

	        KycStepResponse response = new KycStepResponse();
	        boolean isCompleted = false;
			Integer kycStepId = 0;

	        for (KycStepStatus stepStatus : kycStepStatus) {

	            if (stepMaster.getId().equals(stepStatus.getStep().getId())) {
	                isCompleted = stepStatus.getIsCompleted();
	                kycStepId = stepStatus.getId().intValue();
	                break;
	            }
	        }

	        response.setIsCompleted(isCompleted);
	        response.setKycStepId(kycStepId);
	        response.setStepId(stepMaster.getId().intValue());
	        response.setStepName(stepMaster.getStepName());
	        response.setStepOrder(stepMaster.getStepOrder());
	        response.setUserId(userId);

	        kycStepResponses.add(response);
	        
	    }

	    return kycStepResponses;
	}
	
}
