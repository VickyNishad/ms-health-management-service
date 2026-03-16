/**
 * 
 */
package com.health.service;

import java.util.List;

import com.health.dto.request.ClinicRequest;
import com.health.dto.request.DoctorClinicRequest;
import com.health.dto.request.DoctorPersonalDetailsRequest;
import com.health.dto.response.ClinicDetailsDto;
import org.springframework.http.ResponseEntity;

import com.health.models.ApiResponse;
import com.health.dto.MessageResponse;

/**
 * 
 */
public interface DoctorService {
	// DoctorPersonalDetailsRequest
	ResponseEntity<ApiResponse<MessageResponse>> personalDetails(Long userId,DoctorPersonalDetailsRequest doctorPersonalDetailsRequest);

}
