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

import com.health.dto.MessageResponse;
import com.health.models.ApiResponse;
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

	@GetMapping("/{id}")
	public ApiResponse<PatientDto> getPatient(@PathVariable Long id ){
		return patientService.getPatient(id);
	}

	@PostMapping("/{userId}/{id}/update")
	public ApiResponse<PatientDto> updatePatient(@PathVariable Long userId,@PathVariable Long id ,PatientRequest patientRequest){
		return patientService.updatePatient(userId,id,patientRequest);
	}

	@GetMapping("/{userId}/{id}/delete")
	public ApiResponse<MessageResponse> removePatient(@PathVariable Long userId ,@PathVariable Long id){
		return patientService.removePatient(userId,id);
	}
}
