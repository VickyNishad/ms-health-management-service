/**
 * 
 */
package com.health.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.health.dto.response.KycStepResponse;
import com.health.models.ApiResponse;

/**
 * 
 */
public interface KycStepService {
	
	public void addStep(Long userId,Long stepId);
	public void updateStep(Long userId,Long stepId,Long kycStepId);
	public ResponseEntity<ApiResponse<List<KycStepResponse>>> allSteps(Long userId);
	
}
