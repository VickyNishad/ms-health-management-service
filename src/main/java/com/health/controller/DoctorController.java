/**
 * 
 */
package com.health.controller;

import java.util.List;

import com.health.dto.request.ClinicRequest;
import com.health.dto.request.DoctorPersonalDetailsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.health.dto.AvailabilityResponse;
import com.health.dto.DoctorAvailabilityRequest;
import com.health.dto.DoctorAvailabilityUpdateRequest;
import com.health.dto.DoctorLeaveRequest;
import com.health.dto.DoctorLeaveResponse;
import com.health.dto.DoctorProfileRequest;
import com.health.dto.DoctorProfileResponse;
import com.health.dto.MessageResponse;
import com.health.models.ApiResponse;
import com.health.service.DoctorService;


/**
 * 
 */
@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;

//	@PostMapping("/add/profile-details")
//	public ResponseEntity<ApiResponse<MessageResponse>> createProfile(@RequestBody DoctorProfileRequest doctorProfileRequest ){
//		return doctorService.saveProfile(doctorProfileRequest);
//	}
//
//	@GetMapping("/get/profile-details/{doctorId}")
//	public ResponseEntity<ApiResponse<DoctorProfileResponse>> getProfile(@PathVariable(name = "doctorId") Long id){
//		return doctorService.getProfile(id);
//	}
//
//	@PostMapping("/add/leave-or-break")
//	public ResponseEntity<ApiResponse<MessageResponse>> insertDoctorLeaveOrBreak(@RequestBody DoctorLeaveRequest doctorLeaveRequest){
//		return doctorService.createLeaveOrBreak(doctorLeaveRequest);
//	}
//
//	@PostMapping("/update/leave-or-break/{leaveId}")
//	public ResponseEntity<ApiResponse<MessageResponse>> updateDoctorLeaveOrBreak(@PathVariable(name = "leaveId") Long id,@RequestBody DoctorLeaveRequest doctorLeaveRequest){
//		return doctorService.updateLeaveOrBreak(id,doctorLeaveRequest);
//	}
//
//	@GetMapping("/get/leave-or-break/{doctorId}")
//	public ResponseEntity<ApiResponse<List<DoctorLeaveResponse>>> getDoctorLeaves(@PathVariable(name = "doctorId") Long id){
//		return doctorService.getDoctorLeaves(id);
//	}
//
//	@PostMapping("/add/availability/{doctorId}/{clinicId}")
//	public ResponseEntity<ApiResponse<MessageResponse>> insertDoctorAvailablity(@PathVariable(name = "doctorId") Long doctorId,@PathVariable(name = "clinicId") Long clinicId ,@RequestBody DoctorAvailabilityRequest doctorAvailabilityRequest){
//		return doctorService.insertDoctorAvailability(doctorId,clinicId,doctorAvailabilityRequest);
//	}
//
//	@PostMapping("/update/availability/{doctorId}")
//	public ResponseEntity<ApiResponse<MessageResponse>> updateDoctorAvailablity(@PathVariable(name = "doctorId") Long id,@RequestBody DoctorAvailabilityUpdateRequest doctorAvailabilityUpdateRequest){
//		return doctorService.updateDoctorAvailability(id,doctorAvailabilityUpdateRequest);
//	}
//
//	@GetMapping("/get/availability/{doctorId}")
//	public ResponseEntity<ApiResponse<List<AvailabilityResponse>>> getAvailability(@PathVariable(name = "doctorId") Long id){
//		return doctorService.getDoctorAvailability(id);
//	}
//
//	@DeleteMapping("/delete/availability/{doctorId}/{availabilityId}")
//	public ResponseEntity<ApiResponse<MessageResponse>> deleteAvailability(@PathVariable(name = "doctorId") Long id,@PathVariable(name = "availabilityId") Long availabilityId){
//		return doctorService.deleteAvailability(id,availabilityId);
//	}
//

	// personal details

    @PostMapping("/{userId}/personal")
	public ResponseEntity<ApiResponse<MessageResponse>> personalDetails(@PathVariable(name = "userId")Long userId,@RequestBody DoctorPersonalDetailsRequest doctorPersonalDetailsRequest ){
		return doctorService.personalDetails(userId,doctorPersonalDetailsRequest);
	}

	// clinic details

	@PostMapping("/{userId}/clinics")
	public void clinics(@PathVariable(name = "userId") Long userId, @RequestBody List<ClinicRequest> clinicRequests){

	}

	@GetMapping("/{userId}/clinics")
	public void getClinics(@PathVariable(name = "userId") Long userId, @RequestBody List<ClinicRequest> clinicRequests){

	}

	// document details

	// Business day

	// final submit

	// KYC Status

}
