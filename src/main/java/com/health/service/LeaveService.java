/**
 * 
 */
package com.health.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.health.models.ApiResponse;

import com.health.dto.DoctorLeaveRequest;
import com.health.dto.DoctorLeaveResponse;
import com.health.dto.MessageResponse;
import com.health.entity.DoctorLeave;


/**
 * 
 */
public interface LeaveService {

	ApiResponse<MessageResponse> createLeaveOrBreak(DoctorLeaveRequest doctorLeaveRequest);
	ApiResponse<MessageResponse> updateLeaveOrBreak(Long leaveId,DoctorLeaveRequest doctorLeaveRequest);
	ApiResponse<MessageResponse> createUpdateLeaveOrBreak(DoctorLeave existing,DoctorLeaveRequest doctorLeaveRequest);
	ApiResponse<List<DoctorLeaveResponse>> getDoctorLeaves(Long doctorId);
	
}
