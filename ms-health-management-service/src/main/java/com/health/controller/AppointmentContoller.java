/**
 * 
 */
package com.health.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.health.dto.AppointmentBookingRequest;
import com.health.dto.MessageResponse;
import com.health.entity.Appointment;
import com.health.models.ApiResponse;
import com.health.service.AppointmentService;

/**
 * 
 */
@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentContoller {

	@Autowired
	private AppointmentService appointmentService;

	// Create appointment
	@PostMapping
	public ResponseEntity<ApiResponse<MessageResponse>> createAppointment(@RequestParam Long userId,
			@RequestBody AppointmentBookingRequest request) {

		return appointmentService.bookAppointment(userId, request);
	}

	// Get appointments by user
	@GetMapping("/user/{userId}")
	public ResponseEntity<ApiResponse<List<Appointment>>> getAppointmentsByUser(@PathVariable Long userId) {

		return appointmentService.findAllAppointmentsByUser(userId);
	}
	
//	// Get appointments by user with pagination
//	@GetMapping("/user/{userId}")
//	public ResponseEntity<ApiResponse<Page<Appointment>>> getAppointmentsByUser(
//	        @PathVariable Long userId,
//	        @RequestParam(defaultValue = "0") int page,
//	        @RequestParam(defaultValue = "10") int size) {
//
//	    return appointmentService.findAllAppointmentsByUser(userId, page, size);
//	}
	
	

//    Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
//
//    Page<Appointment> appointments = appointmentRepository.findByUserId(userId, pageable);
//
//    ApiResponse<Page<Appointment>> response =
//            new ApiResponse<>(true, "Appointments fetched successfully", appointments);
//
//    return ResponseEntity.ok(response);
}
