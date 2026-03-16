/**
 * 
 */
package com.health.controller;

import java.util.List;

import com.health.dto.request.DoctorClinicRequest;
import com.health.dto.request.DoctorPersonalDetailsRequest;
import com.health.dto.response.ClinicDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/{userId}/personal")
	public ResponseEntity<ApiResponse<MessageResponse>> personalDetails(@PathVariable Long userId, @RequestBody DoctorPersonalDetailsRequest doctorPersonalDetailsRequest ){
		return doctorService.personalDetails(userId,doctorPersonalDetailsRequest);
	}





	// document details

	// Business day

	// final submit

	// KYC Status

}
