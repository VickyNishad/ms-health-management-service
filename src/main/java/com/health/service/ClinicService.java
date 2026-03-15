package com.health.service;

import com.health.dto.MessageResponse;
import com.health.dto.request.ClinicRequest;
import com.health.dto.response.ClinicDetailsDto;
import com.health.entity.Clinic;
import com.health.models.ApiResponse;

import java.util.List;

public interface ClinicService {
    ApiResponse<MessageResponse> createClinic(Long userId , ClinicRequest clinic);
    ApiResponse<MessageResponse> updateClinicById(Long userId ,Long clinicId, ClinicRequest clinic);
    ApiResponse<MessageResponse> deleteClinicById(Long userId , Long clinicId);
    ApiResponse<List<ClinicDetailsDto>> findClinicByDoctorId(Long userId);
}
