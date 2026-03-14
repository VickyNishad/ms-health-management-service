/**
 * 
 */
package com.health.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.health.models.ApiResponse;

import com.health.dto.MessageResponse;
import com.health.dto.SlotDTO;
import com.health.dto.SlotSummaryDTO;


/**
 * 
 */
public interface SlotService {

	public ResponseEntity<ApiResponse<MessageResponse>> generateNext7DaysSlots(Long doctorId, Long clinicId);
	public void getSlots(Long doctorId, Long clinicId, LocalDate date);
	public ResponseEntity<ApiResponse<List<SlotSummaryDTO>>> getSlotSummary(Long doctorId, Long clinicId);
	public ResponseEntity<ApiResponse<List<SlotDTO>>> getSlotsByDoctorClinicAndDate(Long doctorId, Long clinicId, LocalDate date);
}
