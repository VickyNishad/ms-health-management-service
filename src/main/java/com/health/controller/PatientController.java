/**
 * 
 */
package com.health.controller;

import java.util.List;

import com.health.dto.request.PatientRequest;
import com.health.dto.response.PatientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.health.dto.response.MessageResponse;
import com.health.dto.response.ApiResponse;
import com.health.service.PatientService;


/**
 * 
 */
@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@PostMapping("/{userId}/create")
	public ApiResponse<PatientDto> createNewPatient(@PathVariable Long userId, PatientRequest patientRequest){
		return patientService.createNewPatient(userId, patientRequest);
	}
	@GetMapping("/{userId}/all")
	public ApiResponse<List<PatientDto>> getPatients(Long userId ){
		return patientService.getPatients(userId);
	}

	@GetMapping("/{patientId}")
	public ApiResponse<PatientDto> getPatient(@PathVariable Long patientId ){
		return patientService.getPatient(patientId);
	}

	@PostMapping("/{userId}/{patientId}/update")
	public ApiResponse<PatientDto> updatePatient(@PathVariable Long userId,@PathVariable Long patientId ,PatientRequest patientRequest){
		return patientService.updatePatient(userId,patientId,patientRequest);
	}

	@GetMapping("/{userId}/{patientId}/delete")
	public ApiResponse<MessageResponse> removePatient(@PathVariable Long userId ,@PathVariable Long patientId){
		return patientService.removePatient(userId,patientId);
	}
}
