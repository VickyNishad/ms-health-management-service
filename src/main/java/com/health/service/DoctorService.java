/**
 * 
 */
package com.health.service;

import java.util.List;

import com.health.dto.request.AvailabilityRequest;
import com.health.dto.request.ClinicRequest;
import com.health.dto.request.DoctorClinicRequest;
import com.health.dto.request.DoctorPersonalDetailsRequest;
import com.health.dto.response.ClinicDetailsDto;
import com.health.dto.response.DoctorClinicAvailabilityDto;
import com.health.dto.response.DoctorPersonalDetailsDto;
import com.health.entity.DoctorClinicAvailability;
import org.springframework.http.ResponseEntity;

import com.health.models.ApiResponse;
import com.health.dto.MessageResponse;

/**
 * 
 */
public interface DoctorService {
	// DoctorPersonalDetailsRequest
	ResponseEntity<ApiResponse<MessageResponse>> personalDetails(Long userId,DoctorPersonalDetailsRequest doctorPersonalDetailsRequest);
	ApiResponse<DoctorPersonalDetailsDto> getPersonalDetails(Long userId);
	ApiResponse<MessageResponse> createAvailability(Long userId,Long clinicId,AvailabilityRequest request);
	ApiResponse<List<DoctorClinicAvailabilityDto>> getAvailabilityClinicId(Long userId,Long clinicId);
	ApiResponse<List<DoctorClinicAvailabilityDto>> getAvailability(Long userId);


}
