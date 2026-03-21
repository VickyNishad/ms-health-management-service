/**
 * 
 */
package com.health.service;

import java.time.LocalDate;
import java.util.List;

import com.health.dto.response.SlotSummary;
import org.springframework.http.ResponseEntity;

import com.health.models.ApiResponse;

import com.health.dto.MessageResponse;
import com.health.dto.SlotDTO;
import com.health.dto.SlotSummaryDTO;


/**
 * 
 */
public interface SlotService {

	public ApiResponse<MessageResponse> generateNext7DaysSlots(Long userId,Long doctorId);
	public ApiResponse<List<SlotSummary>> getSlots(Long doctorId, Long clinicId, LocalDate date);
	public ApiResponse<List<SlotSummary>> getSlotSummary(Long doctorId, Long clinicId);
	public ApiResponse<List<SlotSummary>> getSlotsByDoctorClinicAndDate(Long doctorId, Long clinicId, LocalDate date);
}
