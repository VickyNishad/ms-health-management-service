/**
 * 
 */
package com.health.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.common.models.ApiResponse;
import com.health.dto.DoctorLeaveRequest;
import com.health.dto.DoctorLeaveResponse;
import com.health.dto.MessageResponse;
import com.health.model.DoctorLeave;


/**
 * 
 */
public interface LeaveService {

	public ResponseEntity<ApiResponse<MessageResponse>> createLeaveOrBreak(DoctorLeaveRequest doctorLeaveRequest);
	public ResponseEntity<ApiResponse<MessageResponse>> updateLeaveOrBreak(Long leaveId,DoctorLeaveRequest doctorLeaveRequest);
	public ResponseEntity<ApiResponse<MessageResponse>> createUpdateLeaveOrBreak(DoctorLeave existing,DoctorLeaveRequest doctorLeaveRequest);
	public ResponseEntity<ApiResponse<List<DoctorLeaveResponse>>> getDoctorLeaves(Long doctorId);
	
}
