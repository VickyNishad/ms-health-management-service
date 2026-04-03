/**
 * 
 */
package com.health.service;

import java.util.List;

import com.health.dto.response.ApiResponse;

import com.health.dto.DoctorLeaveRequest;
import com.health.dto.DoctorLeaveResponse;
import com.health.dto.response.MessageResponse;
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
