/**
 * 
 */
package com.health.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.models.ApiResponse;
import com.health.dto.AddPatientRequest;
import com.health.dto.MessageResponse;
import com.health.model.Patient;
import com.health.service.PatientService;


/**
 * 
 */
@RestController
@RequestMapping("/medicque/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@PostMapping("/add/{userId}")
	public ResponseEntity<ApiResponse<Patient>> addPatient(@PathVariable(name = "userId") Long userId,@RequestBody AddPatientRequest patientRequest){
		return patientService.addPatient(userId, patientRequest);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse<List<Patient>>> getPatient(@PathVariable(name = "userId") Long userId){
		return patientService.getPatient(userId);
	}
	
	@PostMapping("/modify/{userId}/{patientId}")
	public ResponseEntity<ApiResponse<Patient>> modifyPatient(@PathVariable(name = "userId") Long userId,@PathVariable(name = "patientId") Long patientId ,@RequestBody AddPatientRequest patientRequest){
		return patientService.modifyPatient(userId, patientId, patientRequest);
	}
	
	@DeleteMapping("/remove/{userId}/{patientId}")
	public ResponseEntity<ApiResponse<MessageResponse>> removePatient(@PathVariable(name = "userId") Long userId,@PathVariable(name = "patientId") Long patientId){
		return patientService.removePatient(userId, patientId);
	}
}
