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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.models.ApiResponse;
import com.health.dto.AppointmentBookingRequest;
import com.health.dto.MessageResponse;
import com.health.model.Appointment;
import com.health.service.AppointmentService;


/**
 * 
 */
@RestController
@RequestMapping("/medecque/appointment")
public class AppointmentContoller {

	@Autowired
	private AppointmentService appointmentService;
	
	
	@PostMapping("/book/appointment/{userId}")
	public ResponseEntity<ApiResponse<MessageResponse>> bookAppointment(@PathVariable(name = "userId") Long userId ,AppointmentBookingRequest request){
		return appointmentService.bookAppointment(userId,request);
	}
	
	@GetMapping("/all/{userId}")
	public ResponseEntity<ApiResponse<List<Appointment>>> findAllAppointmentsByUser(@PathVariable(name = "userId") Long userId) {
		return appointmentService.findAllAppointmentsByUser(userId);
	}
}
