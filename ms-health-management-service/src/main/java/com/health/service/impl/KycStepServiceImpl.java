/**
 * 
 */
package com.health.service.impl;

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
	
	private List<KycStepResponse> userSteps(Long userId,Long roleId,List<KycStepStatus> kycStepStatus) {
		List<KycStepResponse> kycStepResponses = new ArrayList<>();
		KycStepResponse kycStepResponse = new KycStepResponse();
		for(KycStepStatus kycStatus : kycStepStatus) {
			KycStepMaster kycStepMaster = kycStatus.getStep();
			
			kycStepResponse.setIsCompleted(kycStatus.getIsCompleted());
			kycStepResponse.setStepId(kycStepMaster.getId());
			kycStepResponse.setStepName(kycStepMaster.getStepName());
			kycStepResponse.setStepOrder(kycStepMaster.getStepOrder());
			kycStepResponse.setUserId(userId);
			
			kycStepResponses.add(kycStepResponse);
		}

		return kycStepResponses;
	}
	
	private List<KycStepResponse> doctorSteps(Long userId,Long roleId,List<KycStepStatus> kycStepStatus) {
		List<KycStepResponse> kycStepResponses = new ArrayList<>();
		KycStepResponse kycStepResponse = new KycStepResponse();
		List<KycStepMaster> kycSteps = kycStepMasterRepository.findAll();

		for(KycStepMaster kycStepMaster : kycSteps) {
			for(KycStepStatus stepStatus : kycStepStatus) {
				if(kycStepMaster.getId() == stepStatus.getStep().getId()) {
					kycStepResponse.setIsCompleted(stepStatus.getIsCompleted());
				}
			}
			kycStepResponse.setStepId(kycStepMaster.getId());
			kycStepResponse.setStepName(kycStepMaster.getStepName());
			kycStepResponse.setStepOrder(kycStepMaster.getStepOrder());
			kycStepResponse.setUserId(userId);
			
			kycStepResponses.add(kycStepResponse);
		}

		return kycStepResponses;
	}
	
}
