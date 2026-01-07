/**
 * 
 */
package com.health.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.models.ApiResponse;
import com.health.dto.MessageResponse;
import com.health.dto.SlotDTO;
import com.health.dto.SlotSummaryDTO;
import com.health.service.SlotService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 
 */

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/medicque/slot")
public class SlotController {

	@Autowired
	private SlotService slotService;

	// 1. POST: Generate Next 7 Days Slots
	/**
	 * Endpoint to trigger the generation of time slots for the next 7 days for a
	 * specific doctor at a specific clinic. Maps to: public
	 * ResponseEntity<ApiResponse<MessageResponse>> generateNext7DaysSlots(Long
	 * doctorId, Long clinicId);
	 */
	@PostMapping("/generate/{doctorId}/{clinicId}")
	public ResponseEntity<ApiResponse<MessageResponse>> generateNext7DaysSlots(@PathVariable Long doctorId,
			@PathVariable Long clinicId) {
		ResponseEntity<ApiResponse<MessageResponse>> response = slotService.generateNext7DaysSlots(doctorId, clinicId);
		return response;
	}

	// 2. GET: Get Slots (Note: This method's return type is void in the request,
	// which is unconventional for a public endpoint.
	// We'll assume it performs an internal action or should return a response.)
	/**
	 * Endpoint for internal processing or a scheduled task. Since the request
	 * specified 'void', it might be a temporary or internal trigger. If intended
	 * for public use, its return type should be changed to ResponseEntity. Maps to:
	 * public void getSlots(Long doctorId, Long clinicId, LocalDate date);
	 */
//	@GetMapping("/trigger/{doctorId}/{clinicId}/{date}")
//	public void getSlots(@PathVariable Long doctorId, @PathVariable Long clinicId,@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//		// This method is called, but returns no HTTP response body (200 OK with no body)
//		// If an exception occurs in the service, Spring will handle the error response.
//		slotService.getSlots(doctorId, clinicId, date);
//	}

	// 3. GET: Get Slot Summary
	/**
	 * Retrieves a summary list of slots (e.g., availability count per day). Maps
	 * to: public ResponseEntity<ApiResponse<List<SlotSummaryDTO>>>
	 * getSlotSummary(Long doctorId, Long clinicId);
	 */
	@GetMapping("/summary/{doctorId}/{clinicId}")
	public ResponseEntity<ApiResponse<List<SlotSummaryDTO>>> getSlotSummary(@PathVariable Long doctorId,
			@PathVariable Long clinicId) {
		ResponseEntity<ApiResponse<List<SlotSummaryDTO>>> response = slotService.getSlotSummary(doctorId, clinicId);
		return response;
	}

	// 4. GET: Get Slots by Doctor, Clinic, and Date
	/**
	 * Retrieves detailed list of time slots for a specific date. Maps to: public
	 * ResponseEntity<ApiResponse<List<SlotDTO>>> getSlotsByDoctorClinicAndDate(Long
	 * doctorId, Long clinicId, LocalDate date);
	 */
	@GetMapping("/{doctorId}/{clinicId}/{date}")
	public ResponseEntity<ApiResponse<List<SlotDTO>>> getSlotsByDoctorClinicAndDate(
			@Parameter(description = "Unique ID of the doctor", example = "101", required = true) @PathVariable Long doctorId,

			@Parameter(description = "Unique ID of the clinic", example = "501", required = true) @PathVariable Long clinicId,
			@Parameter(description = "Appointment date in ISO format (yyyy-MM-dd)", example = "2025-01-15", required = true, schema = @Schema(type = "string", format = "date")) 
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		ResponseEntity<ApiResponse<List<SlotDTO>>> response = slotService.getSlotsByDoctorClinicAndDate(doctorId,
				clinicId, date);
		return response;
	}
}
