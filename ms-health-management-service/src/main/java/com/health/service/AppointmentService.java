/**
 * 
 */
package com.health.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.common.models.ApiResponse;
import com.health.dto.AppointmentBookingRequest;
import com.health.dto.MessageResponse;
import com.health.model.Appointment;


/**
 * 
 */
public interface AppointmentService {

	
	public ResponseEntity<ApiResponse<MessageResponse>> bookAppointment(Long userId,AppointmentBookingRequest request);

	public ResponseEntity<ApiResponse<List<Appointment>>> findAllAppointmentsByUser(Long userId);
	
}
