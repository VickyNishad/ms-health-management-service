/**
 * 
 */
package com.health.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.health.entity.*;
import com.health.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.health.dto.response.KycStepResponse;
import com.health.dto.response.ApiResponse;
import com.health.repository.KycStepMasterRepository;
import com.health.repository.KycStepStatusReposotory;
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
	private UserRepository userRepository;

	@Override
	public void addStep(Long userId, Long stepId) {
		// TODO Auto-generated method stub

		Optional<User> user = userRepository.findById(userId);
		if(user.isEmpty()) {
			throw new RuntimeException("User not found. Please create an account to proceed.");
		}
		Optional<KycStepMaster> kycSteps = kycStepMasterRepository.findById(stepId);
		
		KycStepStatus kycStepStatus = new KycStepStatus();
		kycStepStatus.setIsCompleted(true);
		kycStepStatus.setUser(user.get());
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
			Optional<User> user = userRepository.findById(userId);
			if(user.isEmpty()) {
				throw new RuntimeException("User not found. Please create an account to proceed.");
			}
			
			User userData = user.get();
			RoleMaster roleMaster = userData.getRole();
			Long roleId = roleMaster.getId();
			
			List<KycStepResponse> kycStepResponses = new ArrayList<>();
			List<KycStepStatus> kycStepStatus = kycStepStatusReposotory.findByUser_Id(userId);

            if (roleMaster.getId() == 3) {
                kycStepResponses = doctorSteps(userId, roleId, kycStepStatus);
            } else {
                kycStepResponses = userSteps(userId, roleId, kycStepStatus);
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
