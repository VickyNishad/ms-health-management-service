/**
 * 
 */
package com.health.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.health.models.ApiResponse;

import com.health.dto.AddPatientRequest;
import com.health.dto.MessageResponse;
import com.health.entity.Patient;


/**
 * 
 */
public interface PatientService {

	public ResponseEntity<ApiResponse<Patient>> addPatient(Long userId,AddPatientRequest addPatientRequest);
	public ResponseEntity<ApiResponse<List<Patient>>> getPatient(Long userId );
	public ResponseEntity<ApiResponse<Patient>> modifyPatient(Long userId,Long patientId ,AddPatientRequest addPatientRequest);
	public ResponseEntity<ApiResponse<MessageResponse>> removePatient(Long userId ,Long patientId);
	
}
