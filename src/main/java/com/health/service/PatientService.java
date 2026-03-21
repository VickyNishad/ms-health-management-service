/**
 * 
 */
package com.health.service;

import java.util.List;

import com.health.dto.request.PatientRequest;
import com.health.dto.response.PatientDto;
import org.springframework.http.ResponseEntity;

import com.health.models.ApiResponse;

import com.health.dto.AddPatientRequest;
import com.health.dto.MessageResponse;
import com.health.entity.Patient;


/**
 * 
 */
public interface PatientService {

	public ApiResponse<PatientDto> createNewPatient(Long userId, PatientRequest patientRequest);
	public ApiResponse<List<PatientDto>> getPatients(Long userId );
	public ApiResponse<PatientDto> getPatient(Long patientId );
	public ApiResponse<PatientDto> updatePatient(Long userId,Long patientId ,PatientRequest patientRequest);
	public ApiResponse<MessageResponse> removePatient(Long userId ,Long patientId);
	
}
